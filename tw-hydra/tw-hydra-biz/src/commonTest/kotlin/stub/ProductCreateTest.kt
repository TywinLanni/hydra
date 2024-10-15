package stub

import com.github.tywinlanni.hydra.biz.HydraProductProcessor
import com.github.tywinlanni.hydra.common.HydraCorSettings
import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.models.*
import com.github.tywinlanni.hydra.common.stubs.HydraStubs
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ProductCreateTest {
    private val processor = HydraProductProcessor(HydraCorSettings())
    val id = HydraProductId("666")
    val name = "title 666"
    val outerId = "desc 666"
    val productType = HydraProductType.WEIGHT
    val oneUnitWeightG = 1
    val buyStep = 1
    val weighGtPerStorageLocation = 1

    @Test
    fun create() = runTest {
        val ctx = HydraProductContext(
            command = HydraCommand.CREATE,
            state = HydraState.NONE,
            workMode = HydraWorkMode.STUB,
            stubCase = HydraStubs.SUCCESS,
            productRequest = HydraProduct(
                productType = productType,
                name = name,
                outerId = outerId,
                oneUnitWeightG = oneUnitWeightG,
                buyStep = buyStep,
                weighGtPerStorageLocation = weighGtPerStorageLocation,
            )
        )

        processor.exec(ctx)

        assertEquals(name, ctx.productResponse.name)
        assertEquals(outerId, ctx.productResponse.outerId)
        assertEquals(productType, ctx.productResponse.productType)
        assertEquals(oneUnitWeightG, ctx.productResponse.oneUnitWeightG)
        assertEquals(buyStep, ctx.productResponse.buyStep)
        assertEquals(weighGtPerStorageLocation, ctx.productResponse.weighGtPerStorageLocation)
    }

    @Test
    fun badName() = runTest {
        val ctx = HydraProductContext(
            command = HydraCommand.CREATE,
            state = HydraState.NONE,
            workMode = HydraWorkMode.STUB,
            stubCase = HydraStubs.BAD_NAME,
            productRequest = HydraProduct(
                id = id,
                productType = productType,
                name = name,
            )
        )

        processor.exec(ctx)

        assertEquals("bad-name", ctx.errors.firstOrNull()?.code)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun dbError() = runTest {
        val ctx = HydraProductContext(
            command = HydraCommand.CREATE,
            state = HydraState.NONE,
            workMode = HydraWorkMode.STUB,
            stubCase = HydraStubs.DB_ERROR,
            productRequest = HydraProduct(
                id = id,
                productType = productType,
                name = name,
            )
        )

        processor.exec(ctx)

        assertEquals("internal-db", ctx.errors.firstOrNull()?.code)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = HydraProductContext(
            command = HydraCommand.CREATE,
            state = HydraState.NONE,
            workMode = HydraWorkMode.STUB,
            stubCase = HydraStubs.BAD_ID,
            productRequest = HydraProduct(
                id = id,
                productType = productType,
                name = name,
            )
        )

        processor.exec(ctx)

        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}
