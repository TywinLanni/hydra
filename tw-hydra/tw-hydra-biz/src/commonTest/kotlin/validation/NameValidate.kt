package validation

import com.github.tywinlanni.hydra.biz.HydraProductProcessor
import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.models.*
import kotlinx.coroutines.test.runTest
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun validateNameSymbols(command: HydraCommand, processor: HydraProductProcessor) = runTest {
    val ctx = HydraProductContext(
        command = command,
        state = HydraState.NONE,
        workMode = HydraWorkMode.TEST,
        productRequest = HydraProduct(
            id = HydraProductId("123"),
            name = "$#@$%T",
        )
    )

    processor.exec(ctx)

    assertEquals(1, ctx.errors.size)
    assertEquals(HydraState.FAILING, ctx.state)
}

fun validateNameBlank(command: HydraCommand, processor: HydraProductProcessor) = runTest {
    val ctx = HydraProductContext(
        command = command,
        state = HydraState.NONE,
        workMode = HydraWorkMode.TEST,
        productRequest = HydraProduct(
            id = HydraProductId("123"),
            name = "  ",
        )
    )

    processor.exec(ctx)

    assertEquals(1, ctx.errors.size)
    assertEquals(HydraState.FAILING, ctx.state)
}

fun validateNameCorrect(command: HydraCommand, processor: HydraProductProcessor) = runTest {
    val ctx = HydraProductContext(
        command = command,
        state = HydraState.NONE,
        workMode = HydraWorkMode.TEST,
        productRequest = HydraProduct(
            id = HydraProductId("123"),
            name = "name",
        )
    )

    processor.exec(ctx)

    assertEquals(0, ctx.errors.size)
    assertNotEquals(HydraState.FAILING, ctx.state)
}
