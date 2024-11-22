package repo

import com.github.tywinlanni.hydra.common.HydraCorSettings
import com.github.tywinlanni.hydra.common.repo.DbProductResponseOk
import com.github.tywinlanni.hydra.repo.ProductRepositoryMock
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlinx.coroutines.test.runTest
import com.github.tywinlanni.hydra.biz.HydraProductProcessor
import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.models.*


class BizRepoCreateTest {
    private val command = HydraCommand.CREATE
    private val uuid = "10000000-0000-0000-0000-000000000001"
    private val repo = ProductRepositoryMock(
        invokeCreateProduct = {
            DbProductResponseOk(
                data = HydraProduct(
                    id = HydraProductId(uuid),
                    name = it.product.name,
                    productType = it.product.productType,
                )
            )
        }
    )
    private val settings = HydraCorSettings(
        repoTest = repo
    )
    private val processor = HydraProductProcessor(settings)

    @Test
    fun repoCreateSuccessTest() = runTest {
        val ctx = HydraProductContext(
            command = command,
            state = HydraState.NONE,
            workMode = HydraWorkMode.TEST,
            productRequest = HydraProduct(
                name = "abc",
                productType = HydraProductType.WEIGHT,
            ),
        )
        processor.exec(ctx)
        assertEquals(HydraState.FINISHING, ctx.state)
        assertNotEquals(HydraProductId.NONE, ctx.productResponse.id)
        assertEquals("abc", ctx.productResponse.name)
        assertEquals(HydraProductType.WEIGHT, ctx.productResponse.productType)
    }
}
