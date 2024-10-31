package repo

import com.github.tywinlanni.hydra.common.models.HydraCommand
import com.github.tywinlanni.hydra.common.models.HydraProduct
import com.github.tywinlanni.hydra.common.models.HydraProductId
import com.github.tywinlanni.hydra.common.models.HydraProductLock
import com.github.tywinlanni.hydra.common.repo.DbProductResponseErr
import com.github.tywinlanni.hydra.common.repo.DbProductResponseOk
import com.github.tywinlanni.hydra.common.HydraCorSettings
import com.github.tywinlanni.hydra.repo.ProductRepositoryMock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import com.github.tywinlanni.hydra.biz.HydraProductProcessor
import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.models.*

class BizRepoDeleteTest {
    private val command = HydraCommand.DELETE
    private val initProduct = HydraProduct(
        id = HydraProductId("123"),
        lock = HydraProductLock("123-234-abc-ABC"),
        name = "delete"
    )
    private val repo = ProductRepositoryMock(
        invokeReadProduct = {
            DbProductResponseOk(
                data = initProduct,
            )
        },
        invokeDeleteProduct = {
            if (it.id == initProduct.id)
                DbProductResponseOk(
                    data = initProduct
                )
            else DbProductResponseErr()
        }
    )
    private val settings by lazy {
        HydraCorSettings(
            repoTest = repo
        )
    }
    private val processor = HydraProductProcessor(settings)

    @Test
    fun repoDeleteSuccessTest() = runTest {
        val productToUpdate = HydraProduct(
            id = HydraProductId("123"),
            lock = HydraProductLock("123-234-abc-ABC"),
        )
        val ctx = HydraProductContext(
            command = command,
            state = HydraState.NONE,
            workMode = HydraWorkMode.TEST,
            productRequest = productToUpdate,
        )
        processor.exec(ctx)
        assertEquals(HydraState.FINISHING, ctx.state)
        assertTrue { ctx.errors.isEmpty() }
        assertEquals(initProduct.id, ctx.productResponse.id)
        assertEquals(initProduct.name, ctx.productResponse.name)
    }

    @Test
    fun repoDeleteNotFoundTest() = repoNotFoundTest(command)
}