package com.tywinlanni.hydra.repo

import com.github.tywinlanni.hydra.common.models.HydraProductType
import com.github.tywinlanni.hydra.common.repo.*
import com.github.tywinlanni.hydra.stubs.HydraProductStub

class ProductRepoStub() : IRepoProduct {
    override suspend fun createProduct(rq: DbProductRequest): IDbProductResponse {
        return DbProductResponseOk(
            data = HydraProductStub.get(),
        )
    }

    override suspend fun readProduct(rq: DbProductIdRequest): IDbProductResponse {
        return DbProductResponseOk(
            data = HydraProductStub.get(),
        )
    }

    override suspend fun updateProduct(rq: DbProductRequest): IDbProductResponse {
        return DbProductResponseOk(
            data = HydraProductStub.get(),
        )
    }

    override suspend fun deleteProduct(rq: DbProductIdRequest): IDbProductResponse {
        return DbProductResponseOk(
            data = HydraProductStub.get(),
        )
    }

    override suspend fun searchProduct(rq: DbProductFilterRequest): IDbProductsResponse {
        return DbProductsResponseOk(
            data = HydraProductStub.prepareSearchList(filter = "", type = HydraProductType.WEIGHT),
        )
    }
}
