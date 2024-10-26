package com.github.tywinlanni.hydra.common.repo

interface IRepoProduct {
    suspend fun createProduct(rq: DbProductRequest): IDbProductResponse
    suspend fun readProduct(rq: DbProductIdRequest): IDbProductResponse
    suspend fun updateProduct(rq: DbProductRequest): IDbProductResponse
    suspend fun deleteProduct(rq: DbProductIdRequest): IDbProductResponse
    suspend fun searchProduct(rq: DbProductFilterRequest): IDbProductsResponse
    companion object {
        val NONE = object : IRepoProduct {
            override suspend fun createProduct(rq: DbProductRequest): IDbProductResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun readProduct(rq: DbProductIdRequest): IDbProductResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun updateProduct(rq: DbProductRequest): IDbProductResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun deleteProduct(rq: DbProductIdRequest): IDbProductResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun searchProduct(rq: DbProductFilterRequest): IDbProductsResponse {
                throw NotImplementedError("Must not be used")
            }
        }
    }
}
