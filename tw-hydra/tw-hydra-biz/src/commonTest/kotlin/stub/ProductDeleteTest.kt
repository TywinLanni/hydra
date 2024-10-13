package stub

import com.github.tywinlanni.hydra.biz.HydraProductProcessor
import com.github.tywinlanni.hydra.common.HydraCorSettings
import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.models.*
import com.github.tywinlanni.hydra.common.stubs.HydraStubs
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ProductDeleteTest {
    private val processor = HydraProductProcessor(HydraCorSettings())
    val id = HydraProductId("666")

    @Test
    fun delete() = runTest {
        val ctx = HydraProductContext(
            command = HydraCommand.DELETE,
            state = HydraState.NONE,
            workMode = HydraWorkMode.STUB,
            stubCase = HydraStubs.SUCCESS,
            productRequest = HydraProduct(
                id = id,
            )
        )

        processor.exec(ctx)

        assertEquals(id.asString(), ctx.productResponse.id.asString())
    }

    @Test
    fun cannotDelete() = runTest {
        val ctx = HydraProductContext(
            command = HydraCommand.DELETE,
            state = HydraState.NONE,
            workMode = HydraWorkMode.STUB,
            stubCase = HydraStubs.CANNOT_DELETE,
            productRequest = HydraProduct(
                id = id,
            )
        )

        processor.exec(ctx)

        assertEquals("cannot-delete", ctx.errors.firstOrNull()?.code)
        assertEquals("operation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun dbError() = runTest {
        val ctx = HydraProductContext(
            command = HydraCommand.DELETE,
            state = HydraState.NONE,
            workMode = HydraWorkMode.STUB,
            stubCase = HydraStubs.DB_ERROR,
            productRequest = HydraProduct(
                id = id,
            )
        )

        processor.exec(ctx)

        assertEquals("internal-db", ctx.errors.firstOrNull()?.code)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badId() = runTest {
        val ctx = HydraProductContext(
            command = HydraCommand.DELETE,
            state = HydraState.NONE,
            workMode = HydraWorkMode.STUB,
            stubCase = HydraStubs.BAD_ID,
            productRequest = HydraProduct(
                id = id,
            )
        )

        processor.exec(ctx)

        assertEquals("bad-id", ctx.errors.firstOrNull()?.code)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = HydraProductContext(
            command = HydraCommand.DELETE,
            state = HydraState.NONE,
            workMode = HydraWorkMode.STUB,
            stubCase = HydraStubs.BAD_NAME,
            productRequest = HydraProduct(
                id = id,
            )
        )

        processor.exec(ctx)

        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}