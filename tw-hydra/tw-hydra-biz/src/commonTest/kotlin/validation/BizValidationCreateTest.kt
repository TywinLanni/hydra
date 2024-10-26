package validation

import com.github.tywinlanni.hydra.common.models.HydraCommand
import kotlin.test.Test

class BizValidationCreateTest: BaseBizValidationTest() {
    override val command: HydraCommand = HydraCommand.CREATE

    @Test fun correctName() = validateNameCorrect(command, processor)
    @Test fun emptyName() = validateNameBlank(command, processor)
    @Test fun badSymbolsName() = validateNameSymbols(command, processor)
}
