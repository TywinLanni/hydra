package mappers

import com.githib.tywinlanni.hydra.mappers.fromTransport
import com.githib.tywinlanni.hydra.mappers.toTransportProduct
import com.github.tywinlanni.HydraProductContext
import com.github.tywinlanni.hydra.api.v1.models.*
import com.github.tywinlanni.models.*
import com.github.tywinlanni.stubs.HydraStubs
import kotlin.test.Test
import kotlin.test.assertEquals

class MapperDeleteTest {
    @Test
    fun fromTransport() {
        val req = ProductDeleteRequest(
            debug = ProductDebug(
                mode = ProductRequestDebugMode.STUB,
                stub = ProductRequestDebugStubs.SUCCESS,
            ),
            product = ProductDeleteObject(
                id = "1",
                lock = "2",
            )
        )

        val context = HydraProductContext()
        context.fromTransport(req)

        assertEquals(HydraStubs.SUCCESS, context.stubCase)
        assertEquals(HydraWorkMode.STUB, context.workMode)
        assertEquals(HydraProductId("1"), context.productRequest.id)
        assertEquals(HydraProductLock("2"), context.productRequest.lock)
    }

    @Test
    fun toTransport() {
        val context = HydraProductContext(
            requestId = HydraRequestId("1234"),
            command = HydraCommand.DELETE,
            productResponse = HydraProduct(
                id = HydraProductId("1"),
                lock = HydraProductLock("2"),
                name = "asd",
                productType = HydraProductType.WEIGHT,
            ),
            errors = mutableListOf(
                HydraError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = HydraState.RUNNING,
        )

        val req = context.toTransportProduct() as ProductDeleteResponse

        assertEquals("asd", req.product?.name)
        assertEquals("1", req.product?.id)
        assertEquals("2", req.product?.lock)
        assertEquals(ProductType.WEIGHT, req.product?.productType)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("title", req.errors?.firstOrNull()?.field)
        assertEquals("wrong title", req.errors?.firstOrNull()?.message)
    }
}
