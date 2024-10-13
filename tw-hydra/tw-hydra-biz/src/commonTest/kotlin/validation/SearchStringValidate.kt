package validation

import com.github.tywinlanni.hydra.biz.HydraProductProcessor
import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.models.*
import kotlinx.coroutines.test.runTest
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun validateSearchMinLen(command: HydraCommand, processor: HydraProductProcessor) = runTest {
    val ctx = HydraProductContext(
        command = command,
        state = HydraState.NONE,
        workMode = HydraWorkMode.TEST,
        productFilterRequest = HydraProductFilter(
            searchString = "1"
        )
    )

    processor.exec(ctx)

    assertEquals(1, ctx.errors.size)
    assertEquals(HydraState.FAILING, ctx.state)
}

fun validateSearchMaxLen(command: HydraCommand, processor: HydraProductProcessor) = runTest {
    val ctx = HydraProductContext(
        command = command,
        state = HydraState.NONE,
        workMode = HydraWorkMode.TEST,
        productFilterRequest = HydraProductFilter(
            searchString = "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111"
        )
    )

    processor.exec(ctx)

    assertEquals(1, ctx.errors.size)
    assertEquals(HydraState.FAILING, ctx.state)
}

fun validateSearchEmpty(command: HydraCommand, processor: HydraProductProcessor) = runTest {
    val ctx = HydraProductContext(
        command = command,
        state = HydraState.NONE,
        workMode = HydraWorkMode.TEST,
        productFilterRequest = HydraProductFilter(
            searchString = "   "
        )
    )

    processor.exec(ctx)

    assertEquals(1, ctx.errors.size)
    assertEquals(HydraState.FAILING, ctx.state)
}

fun validateSearchCorrect(command: HydraCommand, processor: HydraProductProcessor) = runTest {
    val ctx = HydraProductContext(
        command = command,
        state = HydraState.NONE,
        workMode = HydraWorkMode.TEST,
        productFilterRequest = HydraProductFilter(
            searchString = "test"
        )
    )

    processor.exec(ctx)

    assertEquals(0, ctx.errors.size)
    assertNotEquals(HydraState.FAILING, ctx.state)
}
