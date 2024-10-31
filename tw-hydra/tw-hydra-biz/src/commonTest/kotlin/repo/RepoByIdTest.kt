package repo

import com.github.tywinlanni.hydra.common.models.HydraCommand
import com.github.tywinlanni.hydra.common.models.HydraProduct
import com.github.tywinlanni.hydra.common.models.HydraProductId
import com.github.tywinlanni.hydra.common.models.HydraProductLock
import com.github.tywinlanni.hydra.common.repo.DbProductResponseOk
import com.github.tywinlanni.hydra.common.HydraCorSettings
import com.github.tywinlanni.hydra.repo.ProductRepositoryMock
import kotlinx.coroutines.test.runTest
import kotlin.test.assertEquals
import com.github.tywinlanni.hydra.biz.HydraProductProcessor
import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.models.*
import com.github.tywinlanni.hydra.common.repo.errorNotFound
import kotlin.test.assertNotNull

private val initProduct = HydraProduct(
    id = HydraProductId("123"),
    name = "abc",
    outerId = "abc",
    productType = HydraProductType.WEIGHT,
)
private val repo = ProductRepositoryMock(
    invokeReadProduct = {
        if (it.id == initProduct.id) {
            DbProductResponseOk(
                data = initProduct,
            )
        } else errorNotFound(it.id)
    }
)
private val settings = HydraCorSettings(repoTest = repo)
private val processor = HydraProductProcessor(settings)

fun repoNotFoundTest(command: HydraCommand) = runTest {
    val ctx = HydraProductContext(
        command = command,
        state = HydraState.NONE,
        workMode = HydraWorkMode.TEST,
        productRequest = HydraProduct(
            id = HydraProductId("12345"),
            name = "piTux",
            lock = HydraProductLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(HydraState.FAILING, ctx.state)
    assertEquals(HydraProduct(), ctx.productResponse)
    assertEquals(1, ctx.errors.size)
    assertNotNull(ctx.errors.find { it.code == "repo-not-found" }, "Errors must contain not-found")
}