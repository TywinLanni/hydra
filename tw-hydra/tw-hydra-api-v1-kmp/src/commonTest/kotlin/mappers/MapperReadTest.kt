package mappers

import com.githib.tywinlanni.mappers.fromTransport
import com.githib.tywinlanni.mappers.toTransportProduct
import com.github.tywinlanni.HydraProductContext
import com.github.tywinlanni.hydra.api.v1.models.*
import com.github.tywinlanni.models.*
import com.github.tywinlanni.stubs.HydraStubs
import kotlin.test.Test
import kotlin.test.assertEquals

class MapperReadTest {
    @Test
    fun fromTransport() {
        val req = ProductReadRequest(
            debug = ProductDebug(
                mode = ProductRequestDebugMode.STUB,
                stub = ProductRequestDebugStubs.SUCCESS,
            ),
            product = ProductReadObject(
                id = "1",
            )
        )

        val context = HydraProductContext()
        context.fromTransport(req)

        assertEquals(HydraStubs.SUCCESS, context.stubCase)
        assertEquals(HydraWorkMode.STUB, context.workMode)
        assertEquals(HydraProductId("1"), context.productRequest.id)
    }

    @Test
    fun toTransport() {
        val context = HydraProductContext(
            requestId = HydraRequestId("1234"),
            command = HydraCommand.READ,
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

        val req = context.toTransportProduct() as ProductReadResponse

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