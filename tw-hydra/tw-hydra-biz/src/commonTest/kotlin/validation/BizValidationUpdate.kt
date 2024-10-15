package validation

import com.github.tywinlanni.hydra.common.models.HydraCommand
import kotlin.test.Test

class BizValidationUpdate: BaseBizValidationTest() {
    override val command: HydraCommand = HydraCommand.UPDATE
    @Test
    fun correctId() = validateIdCorrect(command, processor)
    @Test
    fun emptyId() = validateIdBlank(command, processor)
    @Test
    fun badSymbolsId() = validateIdSymbols(command, processor)

    @Test fun correctName() = validateNameCorrect(command, processor)
    @Test fun emptyName() = validateNameBlank(command, processor)
    @Test fun badSymbolsName() = validateNameSymbols(command, processor)

    @Test fun correctLock() = validateLockCorrect(command, processor)
    @Test fun emptyLock() = validateLockBlank(command, processor)
    @Test fun badSymbolsLock() = validateLockSymbols(command, processor)
}
