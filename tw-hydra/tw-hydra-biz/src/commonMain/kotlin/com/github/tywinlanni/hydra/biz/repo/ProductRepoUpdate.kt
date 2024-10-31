package com.github.tywinlanni.hydra.biz.repo

import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.helpers.fail
import com.github.tywinlanni.hydra.common.models.HydraState
import com.github.tywinlanni.hydra.common.repo.DbProductRequest
import com.github.tywinlanni.hydra.common.repo.DbProductResponseErr
import com.github.tywinlanni.hydra.common.repo.DbProductResponseErrWithData
import com.github.tywinlanni.hydra.common.repo.DbProductResponseOk
import com.github.tywinlanni.libs.cor.dsl.ICorChainDsl
import com.github.tywinlanni.libs.cor.dsl.worker

fun ICorChainDsl<HydraProductContext>.repoUpdate(title: String) = worker {
    this.title = title
    on { state == HydraState.RUNNING }
    handle {
        val request = DbProductRequest(productRepoPrepare)
        when(val result = productRepo.updateProduct(request)) {
            is DbProductResponseOk -> productRepoDone = result.data
            is DbProductResponseErr -> fail(result.errors)
            is DbProductResponseErrWithData -> {
                fail(result.errors)
                productRepoDone = result.data
            }
        }
    }
}
