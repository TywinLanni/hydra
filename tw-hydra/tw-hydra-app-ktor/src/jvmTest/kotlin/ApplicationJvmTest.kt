import com.github.tywinlanni.hydra.app.ktor.moduleJvm
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationJvmTest {
    @Test
    fun `root endpoint`() = testApplication {
        application { moduleJvm() }
        val response = client.get("/")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("Hello, world!", response.bodyAsText())
    }
}
