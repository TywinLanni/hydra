import com.githib.tywinlanni.hydra.api1.apiV1Mapper
import com.github.tywinlanni.hydra.api.v1.models.*
import kotlinx.serialization.encodeToString
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestV2SerializationTest {
    private val request: IRequest = ProductCreateRequest(
        debug = ProductDebug(
            mode = ProductRequestDebugMode.STUB,
            stub = ProductRequestDebugStubs.BAD_TITLE,
        ),
        product = ProductCreateObject(
            name = "asd"
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.encodeToString(IRequest.serializer(), request)

        println(json)

        assertContains(json, Regex("\"name\":\\s*\"asd\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badTitle\""))
        assertContains(json, Regex("\"requestType\":\\s*\"createProduct\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.encodeToString(request)
        val obj = apiV1Mapper.decodeFromString<IRequest>(json) as ProductCreateRequest

        assertEquals(request, obj)
    }
    @Test
    fun deserializeNaked() {
        val jsonString = """
            {"product": null}
        """.trimIndent()
        val obj = apiV1Mapper.decodeFromString<ProductCreateRequest>(jsonString)

        assertEquals(null, obj.product)
    }
}
