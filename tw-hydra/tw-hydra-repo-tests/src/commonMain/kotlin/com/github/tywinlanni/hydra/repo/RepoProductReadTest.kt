package com.github.tywinlanni.hydra.repo

import com.github.tywinlanni.hydra.common.models.HydraProduct
import com.github.tywinlanni.hydra.common.models.HydraProductId
import com.github.tywinlanni.hydra.common.repo.DbProductIdRequest
import com.github.tywinlanni.hydra.common.repo.DbProductResponseErr
import com.github.tywinlanni.hydra.common.repo.DbProductResponseOk
import com.github.tywinlanni.hydra.common.repo.IRepoProduct
import kotlin.test.*

abstract class RepoProductReadTest {
    abstract val repo: IRepoProduct
    protected open val readSucc = initObjects[0]

    @Test
    fun readSuccess() = runRepoTest {
        val result = repo.readProduct(DbProductIdRequest(readSucc.id))

        assertIs<DbProductResponseOk>(result)
        assertEquals(readSucc, result.data)
    }

    @Test
    fun readNotFound() = runRepoTest {
        val result = repo.readProduct(DbProductIdRequest(notFoundId))

        assertIs<DbProductResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitProducts("delete") {
        override val initObjects: List<HydraProduct> = listOf(
            createInitTestModel("read")
        )

        val notFoundId = HydraProductId("product-repo-read-notFound")
    }
}
