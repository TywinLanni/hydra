package validation

import com.github.tywinlanni.hydra.common.models.HydraCommand
import kotlin.test.Test

class BizValidationSearch: BaseBizValidationTest() {
    override val command: HydraCommand = HydraCommand.SEARCH

    @Test
    fun correctSearchString() = validateSearchCorrect(command, processor)
    @Test
    fun emptySearchString() = validateSearchEmpty(command, processor)
    @Test
    fun badMinLenSearchString() = validateSearchMinLen(command, processor)
    @Test
    fun badMaxLenSearchString() = validateSearchMaxLen(command, processor)
}
