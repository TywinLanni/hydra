package com.tywinlanni.hydra.repo

import com.github.tywinlanni.hydra.common.models.HydraProduct
import com.github.tywinlanni.hydra.common.models.HydraProductType
import com.github.tywinlanni.hydra.common.repo.DbProductFilterRequest
import com.github.tywinlanni.hydra.common.repo.DbProductsResponseOk
import com.github.tywinlanni.hydra.common.repo.IRepoProduct
import kotlin.test.*

abstract class RepoProductSearchTest {
    abstract val repo: IRepoProduct

    protected open val initializedObjects: List<HydraProduct> = initObjects

    @Test
    fun searchProductType() = runRepoTest {
        val result = repo.searchProduct(DbProductFilterRequest(productType = HydraProductType.WEIGHT))
        assertIs<DbProductsResponseOk>(result)
        val expected = listOf(initializedObjects[2], initializedObjects[4]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data.sortedBy { it.id.asString() })
    }

    @Test
    fun searchProductName() = runRepoTest {
        val result = repo.searchProduct(DbProductFilterRequest(nameFilter = "product1"))
        assertIs<DbProductsResponseOk>(result)
        val expected = initializedObjects[0]
        assertEquals(expected, result.data.firstOrNull())
    }

    companion object: BaseInitProducts("search") {
        override val initObjects: List<HydraProduct> = listOf(
            createInitTestModel("product1"),
            createInitTestModel("product2"),
            createInitTestModel("product3", productType = HydraProductType.WEIGHT),
            createInitTestModel("product4"),
            createInitTestModel("product5", productType = HydraProductType.WEIGHT),
        )
    }
}
