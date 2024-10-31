package repo

import com.github.tywinlanni.hydra.common.models.HydraCommand
import com.github.tywinlanni.hydra.common.models.HydraProduct
import com.github.tywinlanni.hydra.common.models.HydraProductId
import com.github.tywinlanni.hydra.common.repo.DbProductResponseOk
import com.github.tywinlanni.hydra.common.HydraCorSettings
import com.github.tywinlanni.hydra.repo.ProductRepositoryMock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import com.github.tywinlanni.hydra.biz.HydraProductProcessor
import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.models.*

class BizRepoReadTest {
    private val command = HydraCommand.READ
    private val initProduct = HydraProduct(
        id = HydraProductId("123"),
        name = "abc",
        outerId = "abc",
        productType = HydraProductType.WEIGHT,
    )
    private val repo = ProductRepositoryMock(
        invokeReadProduct = {
            DbProductResponseOk(
                data = initProduct,
            )
        }
    )
    private val settings = HydraCorSettings(repoTest = repo)
    private val processor = HydraProductProcessor(settings)

    @Test
    fun repoReadSuccessTest() = runTest {
        val ctx = HydraProductContext(
            command = command,
            state = HydraState.NONE,
            workMode = HydraWorkMode.TEST,
            productRequest = HydraProduct(
                id = HydraProductId("123"),
            ),
        )
        processor.exec(ctx)
        assertEquals(HydraState.FINISHING, ctx.state)
        assertEquals(initProduct.id, ctx.productResponse.id)
        assertEquals(initProduct.name, ctx.productResponse.name)
        assertEquals(initProduct.outerId, ctx.productResponse.outerId)
        assertEquals(initProduct.productType, ctx.productResponse.productType)
    }

    @Test
    fun repoReadNotFoundTest() = repoNotFoundTest(command)
}
