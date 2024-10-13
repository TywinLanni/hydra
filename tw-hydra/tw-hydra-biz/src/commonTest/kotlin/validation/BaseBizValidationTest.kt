package validation

import com.github.tywinlanni.hydra.biz.HydraProductProcessor
import com.github.tywinlanni.hydra.common.HydraCorSettings
import com.github.tywinlanni.hydra.common.models.HydraCommand

abstract class BaseBizValidationTest {
    protected abstract val command: HydraCommand
    private val settings by lazy { HydraCorSettings() }
    protected val processor by lazy { HydraProductProcessor(settings) }
}
