package validation

import com.github.tywinlanni.hydra.biz.HydraProductProcessor
import com.github.tywinlanni.hydra.common.HydraCorSettings
import com.github.tywinlanni.hydra.common.models.HydraCommand
import com.github.tywinlanni.hydra.repo.ProductRepoInMemory
import com.github.tywinlanni.hydra.repo.ProductRepoInitialized
import com.github.tywinlanni.hydra.stubs.HydraProductStub

abstract class BaseBizValidationTest {
    protected abstract val command: HydraCommand
    private val repo = ProductRepoInitialized(
        repo = ProductRepoInMemory(),
        initObjects = listOf(
            HydraProductStub.get(),
        ),
    )
    private val settings by lazy { HydraCorSettings(repoTest = repo) }
    protected val processor by lazy { HydraProductProcessor(settings) }
}
