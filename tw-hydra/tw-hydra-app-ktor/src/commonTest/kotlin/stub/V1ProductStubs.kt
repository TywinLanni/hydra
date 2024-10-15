package stub

import com.githib.tywinlanni.hydra.api1.apiV1Mapper
import com.github.tywinlanni.hydra.app.ktor.module
import com.github.tywinlanni.hydra.api.v1.models.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class V2ProductStubApiTest {

    @Test
    fun create() = v1TestApplication(
        func = "create",
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
        val responseObj = response.body<ProductCreateResponse>()
        assertEquals(200, response.status.value)
        assertEquals("air-req-1", responseObj.product?.id)
        assertEquals("Необходимый воздух", responseObj.product?.name)
    }

    @Test
    fun read() = v1TestApplication(
        func = "read",
        request = ProductReadRequest(
            product = ProductReadObject("air-req-1"),
            debug = ProductDebug(
                mode = ProductRequestDebugMode.STUB,
                stub = ProductRequestDebugStubs.SUCCESS
            )
        ),
    ) { response ->
        val responseObj = response.body<ProductReadResponse>()
        assertEquals(200, response.status.value)
        assertEquals("air-req-1", responseObj.product?.id)
    }

    @Test
    fun update() = v1TestApplication(
        func = "update",
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
        val responseObj = response.body<ProductUpdateResponse>()
        assertEquals(200, response.status.value)
        assertEquals("air-req-1", responseObj.product?.id)
        assertEquals("Необходимый воздух", responseObj.product?.name)
    }

    @Test
    fun delete() = v1TestApplication(
        func = "delete",
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
        val responseObj = response.body<ProductDeleteResponse>()
        assertEquals(200, response.status.value)
        assertEquals("air-req-1", responseObj.product?.id)
    }

    @Test
    fun search() = v1TestApplication(
        func = "search",
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
        val responseObj = response.body<ProductSearchResponse>()
        assertEquals(200, response.status.value)
        assertEquals("product search-1", responseObj.products?.first()?.id)
    }

    private inline fun <reified T: IRequest> v1TestApplication(
        func: String,
        request: T,
        crossinline function: suspend (HttpResponse) -> Unit,
    ): Unit = testApplication {
        application { module() }
        val client = createClient {
            install(ContentNegotiation) {
                json(apiV1Mapper)
            }
        }
        val response = client.post("/v1/product/$func") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        function(response)
    }
}