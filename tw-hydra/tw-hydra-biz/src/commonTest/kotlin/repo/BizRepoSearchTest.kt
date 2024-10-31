package repo

import com.github.tywinlanni.hydra.common.models.HydraCommand
import com.github.tywinlanni.hydra.common.models.HydraProduct
import com.github.tywinlanni.hydra.common.models.HydraProductId
import com.github.tywinlanni.hydra.common.HydraCorSettings
import com.github.tywinlanni.hydra.repo.ProductRepositoryMock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import com.github.tywinlanni.hydra.biz.HydraProductProcessor
import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.models.*
import com.github.tywinlanni.hydra.common.repo.DbProductsResponseOk

class BizRepoSearchTest {
    private val command = HydraCommand.SEARCH
    private val initProduct = HydraProduct(
        id = HydraProductId("123"),
        name = "abc",
    )
    private val repo = ProductRepositoryMock(
        invokeSearchProduct = {
            DbProductsResponseOk(
                data = listOf(initProduct),
            )
        }
    )
    private val settings = HydraCorSettings(repoTest = repo)
    private val processor = HydraProductProcessor(settings)

    @Test
    fun repoSearchSuccessTest() = runTest {
        val ctx = HydraProductContext(
            command = command,
            state = HydraState.NONE,
            workMode = HydraWorkMode.TEST,
            productFilterRequest = HydraProductFilter(
                searchString = "abc",
                productType = HydraProductType.WEIGHT
            ),
        )
        processor.exec(ctx)
        assertEquals(HydraState.FINISHING, ctx.state)
        assertEquals(1, ctx.productsResponse.size)
    }
}
