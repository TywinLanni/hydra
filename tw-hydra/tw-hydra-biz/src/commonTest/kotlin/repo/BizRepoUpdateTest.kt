package repo

import com.github.tywinlanni.hydra.common.models.HydraCommand
import com.github.tywinlanni.hydra.common.models.HydraProduct
import com.github.tywinlanni.hydra.common.models.HydraProductId
import com.github.tywinlanni.hydra.common.models.HydraProductLock
import com.github.tywinlanni.hydra.common.repo.DbProductResponseOk
import com.github.tywinlanni.hydra.common.HydraCorSettings
import com.github.tywinlanni.hydra.repo.ProductRepositoryMock
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import com.github.tywinlanni.hydra.biz.HydraProductProcessor
import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.models.*


class BizRepoUpdateTest {
    private val command = HydraCommand.UPDATE
    private val initProduct = HydraProduct(
        id = HydraProductId("123"),
        name = "abc",
        outerId = "abc",
        productType = HydraProductType.WEIGHT,
        lock = HydraProductLock("123-234-abc-ABC"),
    )
    private val repo = ProductRepositoryMock(
        invokeReadProduct = {
            DbProductResponseOk(
                data = initProduct,
            )
        },
        invokeUpdateProduct = {
            DbProductResponseOk(
                data = HydraProduct(
                    id = HydraProductId("123"),
                    name = "xyz",
                    outerId = "xyz",
                    productType = HydraProductType.WEIGHT,
                    lock = HydraProductLock("123-234-abc-ABC"),
                )
            )
        }
    )
    private val settings = HydraCorSettings(repoTest = repo)
    private val processor = HydraProductProcessor(settings)

    @Test
    fun repoUpdateSuccessTest() = runTest {
        val productToUpdate = HydraProduct(
            id = HydraProductId("123"),
            name = "xyz",
            outerId = "xyz",
            productType = HydraProductType.WEIGHT,
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
        assertEquals(productToUpdate.id, ctx.productResponse.id)
        assertEquals(productToUpdate.name, ctx.productResponse.name)
        assertEquals(productToUpdate.outerId, ctx.productResponse.outerId)
        assertEquals(productToUpdate.productType, ctx.productResponse.productType)
    }

    @Test
    fun repoUpdateNotFoundTest() = repoNotFoundTest(command)
}
