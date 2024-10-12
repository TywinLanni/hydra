package mappers

import com.githib.tywinlanni.hydra.api1.mappers.fromTransportValidated
import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.api.v1.models.ProductCreateRequest
import com.github.tywinlanni.hydra.api.v1.models.ProductDebug
import com.github.tywinlanni.hydra.api.v1.models.ProductRequestDebugStubs
import com.github.tywinlanni.hydra.common.stubs.HydraStubs
import kotlin.test.Test
import kotlin.test.assertEquals

class MapperValidatedTest {
    @Test
    fun fromTransportValidated() {
        val req = ProductCreateRequest(
            debug = ProductDebug(
                stub = ProductRequestDebugStubs.SUCCESS,
            ),
        )

        val context = HydraProductContext()
        context.fromTransportValidated(req)

        assertEquals(HydraStubs.SUCCESS, context.stubCase)
    }
}
