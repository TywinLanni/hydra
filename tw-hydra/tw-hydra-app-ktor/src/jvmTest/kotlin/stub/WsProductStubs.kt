package stub

import com.githib.tywinlanni.hydra.api1.apiV1Mapper
import com.githib.tywinlanni.hydra.api1.apiV1RequestSerialize
import com.githib.tywinlanni.hydra.api1.apiV1ResponseDeserialize
import com.github.tywinlanni.hydra.api.v1.models.*
import com.github.tywinlanni.hydra.app.ktor.module
import com.github.tywinlanni.hydra.app.ktor.moduleJvm
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import io.ktor.websocket.*
import kotlin.test.Test
import kotlin.test.assertEquals

class WsProductStubs {

    @Test
    fun create() = wsTestApplication(
        request = ProductCreateRequest(
            product = ProductCreateObject(
                name = "test"
            ),
            debug = ProductDebug(
                mode = ProductRequestDebugMode.STUB,
                stub = ProductRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response as ProductCreateResponse
        assertEquals("air-req-1", responseObj.product?.id)
        assertEquals("test", responseObj.product?.name)
    }

    @Test
    fun read() = wsTestApplication(
        request = ProductReadRequest(
            product = ProductReadObject("air-req-1"),
            debug = ProductDebug(
                mode = ProductRequestDebugMode.STUB,
                stub = ProductRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response as ProductReadResponse
        assertEquals("air-req-1", responseObj.product?.id)
    }

    @Test
    fun update() = wsTestApplication(
        request = ProductUpdateRequest(
            product = ProductUpdateObject(
                id = "air-req-1",
                name = "Необходимый воздух"
            ),
            debug = ProductDebug(
                mode = ProductRequestDebugMode.STUB,
                stub = ProductRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response as ProductUpdateResponse
        assertEquals("air-req-1", responseObj.product?.id)
        assertEquals("Необходимый воздух", responseObj.product?.name)
    }

    @Test
    fun delete() = wsTestApplication(
        request = ProductDeleteRequest(
            product = ProductDeleteObject(
                id = "air-req-1",
                lock = "123"
            ),
            debug = ProductDebug(
                mode = ProductRequestDebugMode.STUB,
                stub = ProductRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response as ProductDeleteResponse
        assertEquals("air-req-1", responseObj.product?.id)
    }

    @Test
    fun search() = wsTestApplication(
        request = ProductSearchRequest(
            productFilter = ProductSearchFilter(
                searchString = "kotlin"
            ),
            debug = ProductDebug(
                mode = ProductRequestDebugMode.STUB,
                stub = ProductRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response as ProductSearchResponse
        assertEquals("kotlin-1", responseObj.products?.first()?.id)
    }

    private inline fun <reified T: IRequest> wsTestApplication(
        request: T,
        crossinline function: suspend (IResponse?) -> Unit,
    ): Unit = testApplication {
        application { moduleJvm() }
        val client = createClient {
            install(WebSockets)

            install(ContentNegotiation) {
                json(apiV1Mapper)
            }
        }

        var response: IResponse? = null

        client.webSocket("/v1/ws") {
            val initResponse = apiV1ResponseDeserialize<IResponse>((incoming.receive() as? Frame.Text)?.readText() ?: "")

            assertEquals(ResponseResult.SUCCESS, initResponse.result)

            send(Frame.Text(apiV1RequestSerialize(request)))

            response = apiV1ResponseDeserialize((incoming.receive() as Frame.Text).readText())
        }

        function(response)
    }
}
