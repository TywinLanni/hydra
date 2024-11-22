package com.github.tywinlanni.hydra.repo

import com.github.tywinlanni.hydra.common.models.HydraProduct
import com.github.tywinlanni.hydra.common.repo.*

class ProductRepositoryMock(
    private val invokeCreateProduct: (DbProductRequest) -> IDbProductResponse = { DEFAULT_PRODUCT_SUCCESS_EMPTY_MOCK },
    private val invokeReadProduct: (DbProductIdRequest) -> IDbProductResponse = { DEFAULT_PRODUCT_SUCCESS_EMPTY_MOCK },
    private val invokeUpdateProduct: (DbProductRequest) -> IDbProductResponse = { DEFAULT_PRODUCT_SUCCESS_EMPTY_MOCK },
    private val invokeDeleteProduct: (DbProductIdRequest) -> IDbProductResponse = { DEFAULT_PRODUCT_SUCCESS_EMPTY_MOCK },
    private val invokeSearchProduct: (DbProductFilterRequest) -> IDbProductsResponse = { DEFAULT_PRODUCTS_SUCCESS_EMPTY_MOCK },
): IRepoProduct {
    override suspend fun createProduct(rq: DbProductRequest): IDbProductResponse {
        return invokeCreateProduct(rq)
    }

    override suspend fun readProduct(rq: DbProductIdRequest): IDbProductResponse {
        return invokeReadProduct(rq)
    }

    override suspend fun updateProduct(rq: DbProductRequest): IDbProductResponse {
        return invokeUpdateProduct(rq)
    }

    override suspend fun deleteProduct(rq: DbProductIdRequest): IDbProductResponse {
        return invokeDeleteProduct(rq)
    }

    override suspend fun searchProduct(rq: DbProductFilterRequest): IDbProductsResponse {
        return invokeSearchProduct(rq)
    }

    companion object {
        val DEFAULT_PRODUCT_SUCCESS_EMPTY_MOCK = DbProductResponseOk(HydraProduct())
        val DEFAULT_PRODUCTS_SUCCESS_EMPTY_MOCK = DbProductsResponseOk(emptyList())
    }
}
