package mappers

import com.githib.tywinlanni.mappers.fromTransport
import com.githib.tywinlanni.mappers.toTransportProduct
import com.github.tywinlanni.HydraProductContext
import com.github.tywinlanni.hydra.api.v1.models.*
import com.github.tywinlanni.models.*
import com.github.tywinlanni.stubs.HydraStubs
import kotlin.test.Test
import kotlin.test.assertEquals

class MapperCreateTest {
    @Test
    fun fromTransport() {
        val req = ProductCreateRequest(
            debug = ProductDebug(
                mode = ProductRequestDebugMode.STUB,
                stub = ProductRequestDebugStubs.SUCCESS,
            ),
            product = ProductCreateObject(
                name = "asd"
            )
        )

        val context = HydraProductContext()
        context.fromTransport(req)

        assertEquals(HydraStubs.SUCCESS, context.stubCase)
        assertEquals(HydraWorkMode.STUB, context.workMode)
        assertEquals("asd", context.productRequest.name)
    }

    @Test
    fun toTransport() {
        val context = HydraProductContext(
            requestId = HydraRequestId("1234"),
            command = HydraCommand.CREATE,
            productResponse = HydraProduct(
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

        val req = context.toTransportProduct() as ProductCreateResponse

        assertEquals("asd", req.product?.name)
        assertEquals(ProductType.WEIGHT, req.product?.productType)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("title", req.errors?.firstOrNull()?.field)
        assertEquals("wrong title", req.errors?.firstOrNull()?.message)
    }
}
