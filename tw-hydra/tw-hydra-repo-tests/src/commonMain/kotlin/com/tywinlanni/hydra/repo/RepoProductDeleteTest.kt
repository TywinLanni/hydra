package com.tywinlanni.hydra.repo

import com.github.tywinlanni.hydra.common.models.HydraProduct
import com.github.tywinlanni.hydra.common.models.HydraProductId
import com.github.tywinlanni.hydra.common.repo.DbProductIdRequest
import com.github.tywinlanni.hydra.common.repo.DbProductResponseErr
import com.github.tywinlanni.hydra.common.repo.DbProductResponseOk
import com.github.tywinlanni.hydra.common.repo.IRepoProduct
import kotlin.test.*

abstract class RepoProductDeleteTest {
    abstract val repo: IRepoProduct
    protected open val deleteSucc = initObjects[0]
    protected open val notFoundId = HydraProductId("product-repo-delete-notFound")

    @Test
    fun deleteSuccess() = runRepoTest {
        val result = repo.deleteProduct(DbProductIdRequest(deleteSucc.id))
        assertIs<DbProductResponseOk>(result)
        assertEquals(deleteSucc.name, result.data.name)
        assertEquals(deleteSucc.outerId, result.data.outerId)
    }

    @Test
    fun deleteNotFound() = runRepoTest {
        val result = repo.readProduct(DbProductIdRequest(notFoundId))

        assertIs<DbProductResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertNotNull(error)
    }

    companion object : BaseInitProducts("delete") {
        override val initObjects: List<HydraProduct> = listOf(
            createInitTestModel("delete"),
        )
    }
}
