package mappers;

import com.githib.tywinlanni.mappers.fromTransport
import com.githib.tywinlanni.mappers.toTransportProduct
import com.github.tywinlanni.HydraProductContext
import com.github.tywinlanni.hydra.api.v1.models.*
import com.github.tywinlanni.models.*
import com.github.tywinlanni.stubs.HydraStubs
import kotlin.test.Test
import kotlin.test.assertEquals

class MapperSearchTest {
    @Test
    fun fromTransport() {
        val req = ProductSearchRequest(
            debug = ProductDebug(
                mode = ProductRequestDebugMode.STUB,
                stub = ProductRequestDebugStubs.SUCCESS,
            ),
            productFilter = ProductSearchFilter(
                searchString = "asd",
                productType = ProductType.WEIGHT,
                planedForPurchase = true,
            )
        )

        val context = HydraProductContext()
        context.fromTransport(req)

        assertEquals(HydraStubs.SUCCESS, context.stubCase)
        assertEquals(HydraWorkMode.STUB, context.workMode)
        assertEquals("asd", context.productFilterRequest.searchString)
        assertEquals(HydraProductType.WEIGHT, context.productFilterRequest.productType)
        assertEquals(HydraProductPlanedForPurchase.ONLY_PLANNED, context.productFilterRequest.planedForPurchase)
    }

    @Test
    fun toTransport() {
        val context = HydraProductContext(
            requestId = HydraRequestId("1234"),
            command = HydraCommand.SEARCH,
            productsResponse = mutableListOf(
                HydraProduct(
                    id = HydraProductId("1"),
                    lock = HydraProductLock("2"),
                    name = "asd",
                    productType = HydraProductType.WEIGHT,
                )
            ),
            errors = mutableListOf(),
            state = HydraState.RUNNING,
        )

        val req = context.toTransportProduct() as ProductSearchResponse

        assertEquals("asd", req.products?.first()?.name)
        assertEquals("1", req.products?.first()?.id)
        assertEquals("2", req.products?.first()?.lock)
        assertEquals(ProductType.WEIGHT, req.products?.first()?.productType)
        assertEquals(1, req.products?.size)
    }
}
