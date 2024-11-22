package validation

import com.github.tywinlanni.hydra.biz.HydraProductProcessor
import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.models.*
import kotlinx.coroutines.test.runTest
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun validateLockSymbols(command: HydraCommand, processor: HydraProductProcessor) = runTest {
    val ctx = HydraProductContext(
        command = command,
        state = HydraState.NONE,
        workMode = HydraWorkMode.TEST,
        productRequest = HydraProduct(
            id = HydraProductId("123"),
            name = "correct",
            lock = HydraProductLock("$#@$%T"),
        )
    )

    processor.exec(ctx)

    assertEquals(1, ctx.errors.size)
    assertEquals(HydraState.FAILING, ctx.state)
}

fun validateLockBlank(command: HydraCommand, processor: HydraProductProcessor) = runTest {
    val ctx = HydraProductContext(
        command = command,
        state = HydraState.NONE,
        workMode = HydraWorkMode.TEST,
        productRequest = HydraProduct(
            id = HydraProductId("123"),
            name = "correct",
            lock = HydraProductLock("  "),
        )
    )

    processor.exec(ctx)

    assertEquals(1, ctx.errors.size)
    assertEquals(HydraState.FAILING, ctx.state)
}

fun validateLockCorrect(command: HydraCommand, processor: HydraProductProcessor) = runTest {
    val ctx = HydraProductContext(
        command = command,
        state = HydraState.NONE,
        workMode = HydraWorkMode.TEST,
        productRequest = HydraProduct(
            id = HydraProductId("air-req-1"),
            name = "correct",
            lock = HydraProductLock("lock"),
        )
    )

    processor.exec(ctx)

    assertEquals(0, ctx.errors.size)
    assertNotEquals(HydraState.FAILING, ctx.state)
}