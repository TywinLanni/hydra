package com.tywinlanni.hydra.repo

import com.github.tywinlanni.hydra.common.models.HydraProduct
import com.github.tywinlanni.hydra.common.models.HydraProductId
import com.github.tywinlanni.hydra.common.models.HydraProductType
import com.github.tywinlanni.hydra.common.repo.DbProductRequest
import com.github.tywinlanni.hydra.common.repo.DbProductResponseErr
import com.github.tywinlanni.hydra.common.repo.DbProductResponseOk
import com.github.tywinlanni.hydra.common.repo.IRepoProduct
import kotlin.test.*

abstract class RepoProductUpdateTest {
    abstract val repo: IRepoProduct
    protected open val updateSucc = initObjects[0]
    protected val updateIdNotFound = HydraProductId("product-repo-update-not-found")

    private val reqUpdateSucc by lazy {
        HydraProduct(
            id = updateSucc.id,
            name = "update object",
            outerId = "update object description",
            productType = HydraProductType.QUANTITY,
        )
    }
    private val reqUpdateNotFound = HydraProduct(
        id = updateIdNotFound,
        name = "update object not found",
        outerId = "update object not found description",
    )

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateProduct(DbProductRequest(reqUpdateSucc))
        assertIs<DbProductResponseOk>(result)
        assertEquals(reqUpdateSucc.id, result.data.id)
        assertEquals(reqUpdateSucc.name, result.data.name)
        assertEquals(reqUpdateSucc.outerId, result.data.outerId)
        assertEquals(reqUpdateSucc.productType, result.data.productType)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateProduct(DbProductRequest(reqUpdateNotFound))
        assertIs<DbProductResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitProducts("update") {
        override val initObjects: List<HydraProduct> = listOf(
            createInitTestModel("update"),
        )
    }
}
