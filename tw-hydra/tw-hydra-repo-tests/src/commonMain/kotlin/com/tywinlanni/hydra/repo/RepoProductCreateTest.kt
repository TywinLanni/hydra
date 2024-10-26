package com.tywinlanni.hydra.repo

import com.github.tywinlanni.hydra.common.models.HydraProduct
import com.github.tywinlanni.hydra.common.models.HydraProductId
import com.github.tywinlanni.hydra.common.models.HydraProductType
import com.github.tywinlanni.hydra.common.repo.DbProductRequest
import com.github.tywinlanni.hydra.common.repo.DbProductResponseOk
import kotlin.test.*

abstract class RepoProductCreateTest {
    abstract val repo: IRepoProductInitializable
    protected open val uuidNew = HydraProductId("10000000-0000-0000-0000-000000000001")

    private val createObj = HydraProduct(
        name = "create object",
        outerId = "create object outerId",
        productType = HydraProductType.WEIGHT,
    )

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createProduct(DbProductRequest(createObj))
        val expected = createObj
        assertIs<DbProductResponseOk>(result)
        assertEquals(uuidNew, result.data.id)
        assertEquals(expected.name, result.data.name)
        assertEquals(expected.outerId, result.data.outerId)
        assertEquals(expected.productType, result.data.productType)
        assertNotEquals(HydraProductId.NONE, result.data.id)
    }

    companion object : BaseInitProducts("create") {
        override val initObjects: List<HydraProduct> = emptyList()
    }
}
