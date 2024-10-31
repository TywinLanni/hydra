package com.github.tywinlanni.hydra.biz.repo

import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.helpers.fail
import com.github.tywinlanni.hydra.common.models.HydraState
import com.github.tywinlanni.hydra.common.repo.DbProductFilterRequest
import com.github.tywinlanni.hydra.common.repo.DbProductsResponseErr
import com.github.tywinlanni.hydra.common.repo.DbProductsResponseOk
import com.github.tywinlanni.libs.cor.dsl.ICorChainDsl
import com.github.tywinlanni.libs.cor.dsl.worker

fun ICorChainDsl<HydraProductContext>.repoSearch(title: String) = worker {
    this.title = title
    description = "Поиск продуктов в БД по фильтру"
    on { state == HydraState.RUNNING }
    handle {
        val request = DbProductFilterRequest(
            nameFilter = productFilterValidating.searchString,
            productType = productFilterValidating.productType,
            planedForPurchase = productFilterValidating.planedForPurchase,
        )
        when(val result = productRepo.searchProduct(request)) {
            is DbProductsResponseOk -> productsRepoDone = result.data.toMutableList()
            is DbProductsResponseErr -> fail(result.errors)
        }
    }
}
