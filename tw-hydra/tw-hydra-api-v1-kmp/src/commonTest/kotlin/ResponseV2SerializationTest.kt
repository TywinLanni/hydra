import com.githib.tywinlanni.hydra.apiV1Mapper
import com.github.tywinlanni.hydra.api.v1.models.IResponse
import com.github.tywinlanni.hydra.api.v1.models.ProductCreateResponse
import com.github.tywinlanni.hydra.api.v1.models.ProductResponseObject
import kotlinx.serialization.encodeToString
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseV2SerializationTest {
    private val response: IResponse = ProductCreateResponse(
        product = ProductResponseObject(
            name = "some name",
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.encodeToString(response)

        println(json)

        assertContains(json, Regex("\"name\":\\s*\"some name\""))
        assertContains(json, Regex("\"responseType\":\\s*\"createProduct\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.encodeToString(response)
        val obj = apiV1Mapper.decodeFromString<IResponse>(json) as ProductCreateResponse

        assertEquals(response, obj)
    }
}