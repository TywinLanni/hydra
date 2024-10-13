package validation

import com.github.tywinlanni.hydra.common.models.HydraCommand
import kotlin.test.Test

class BizValidationDeleteTest: BaseBizValidationTest() {
    override val command: HydraCommand = HydraCommand.DELETE

    @Test
    fun correctId() = validateIdCorrect(command, processor)
    @Test
    fun emptyId() = validateIdBlank(command, processor)
    @Test
    fun badSymbolsId() = validateIdSymbols(command, processor)
}
