package validation

import com.github.tywinlanni.hydra.common.models.HydraCommand
import kotlin.test.Test

class BizValidationReadTest: BaseBizValidationTest() {
    override val command: HydraCommand = HydraCommand.READ

    @Test
    fun correctId() = validateIdCorrect(command, processor)
    @Test
    fun emptyId() = validateIdBlank(command, processor)
    @Test
    fun badSymbolsId() = validateIdSymbols(command, processor)
}
