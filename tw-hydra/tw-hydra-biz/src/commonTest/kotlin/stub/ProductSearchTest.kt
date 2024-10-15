package stub

import com.github.tywinlanni.hydra.biz.HydraProductProcessor
import com.github.tywinlanni.hydra.common.HydraCorSettings
import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.models.*
import com.github.tywinlanni.hydra.common.stubs.HydraStubs
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ProductSearchTest {
    private val processor = HydraProductProcessor(HydraCorSettings())
    val searchString = "asda"

    @Test
    fun search() = runTest {
        val ctx = HydraProductContext(
            command = HydraCommand.SEARCH,
            state = HydraState.NONE,
            workMode = HydraWorkMode.STUB,
            stubCase = HydraStubs.SUCCESS,
            productFilterRequest = HydraProductFilter(
                searchString = searchString,
            )
        )

        processor.exec(ctx)

        assertContains(ctx.productsResponse.first().name, searchString)
    }

    @Test
    fun badSearch() = runTest {
        val ctx = HydraProductContext(
            command = HydraCommand.SEARCH,
            state = HydraState.NONE,
            workMode = HydraWorkMode.STUB,
            stubCase = HydraStubs.BAD_SEARCH_STRING,
            productFilterRequest = HydraProductFilter(
                searchString = searchString,
            )
        )

        processor.exec(ctx)

        assertEquals("bad-search-string", ctx.errors.firstOrNull()?.code)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun dbError() = runTest {
        val ctx = HydraProductContext(
            command = HydraCommand.DELETE,
            state = HydraState.NONE,
            workMode = HydraWorkMode.STUB,
            stubCase = HydraStubs.DB_ERROR,
            productFilterRequest = HydraProductFilter(
                searchString = searchString,
            )
        )

        processor.exec(ctx)

        assertEquals("internal-db", ctx.errors.firstOrNull()?.code)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = HydraProductContext(
            command = HydraCommand.DELETE,
            state = HydraState.NONE,
            workMode = HydraWorkMode.STUB,
            stubCase = HydraStubs.BAD_NAME,
            productFilterRequest = HydraProductFilter(
                searchString = searchString,
            )
        )

        processor.exec(ctx)

        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}
