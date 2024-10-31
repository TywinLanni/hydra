package com.github.tywinlanni

import com.github.tywinlanni.hydra.common.repo.*
import com.github.tywinlanni.hydra.common.models.*
import com.github.tywinlanni.hydra.stubs.HydraProductStub
import com.github.tywinlanni.hydra.repo.ProductRepositoryMock
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlinx.coroutines.test.runTest

class ProductRepositoryMockTest {
    private val repo = com.github.tywinlanni.hydra.repo.ProductRepositoryMock(
        invokeCreateProduct = { DbProductResponseOk(HydraProductStub.prepareProduct { name = "create" }) },
        invokeReadProduct = { DbProductResponseOk(HydraProductStub.prepareProduct { name = "read" }) },
        invokeUpdateProduct = { DbProductResponseOk(HydraProductStub.prepareProduct { name = "update" }) },
        invokeDeleteProduct = { DbProductResponseOk(HydraProductStub.prepareProduct { name = "delete" }) },
        invokeSearchProduct = { DbProductsResponseOk(listOf(HydraProductStub.prepareProduct { name = "search" })) },
    )

    @Test
    fun mockCreate() = runTest {
        val result = repo.createProduct(DbProductRequest(HydraProduct()))
        assertIs<DbProductResponseOk>(result)
        assertEquals("create", result.data.name)
    }

    @Test
    fun mockRead() = runTest {
        val result = repo.readProduct(DbProductIdRequest(HydraProduct()))
        assertIs<DbProductResponseOk>(result)
        assertEquals("read", result.data.name)
    }

    @Test
    fun mockUpdate() = runTest {
        val result = repo.updateProduct(DbProductRequest(HydraProduct()))
        assertIs<DbProductResponseOk>(result)
        assertEquals("update", result.data.name)
    }

    @Test
    fun mockDelete() = runTest {
        val result = repo.deleteProduct(DbProductIdRequest(HydraProduct()))
        assertIs<DbProductResponseOk>(result)
        assertEquals("delete", result.data.name)
    }

    @Test
    fun mockSearch() = runTest {
        val result = repo.searchProduct(DbProductFilterRequest())
        assertIs<DbProductsResponseOk>(result)
        assertEquals("search", result.data.first().name)
    }
}
