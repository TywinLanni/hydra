package com.github.tywinlanni.hydra.biz.repo

import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.helpers.fail
import com.github.tywinlanni.hydra.common.models.HydraState
import com.github.tywinlanni.hydra.common.repo.*
import com.github.tywinlanni.libs.cor.dsl.ICorChainDsl
import com.github.tywinlanni.libs.cor.dsl.worker

fun ICorChainDsl<HydraProductContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "Удаление объявления из БД по ID"
    on { state == HydraState.RUNNING }
    handle {
        val request = DbProductIdRequest(productRepoPrepare)

        when(val result = productRepo.deleteProduct(request)) {
            is DbProductResponseOk -> productRepoDone = result.data
            is DbProductResponseErr -> {
                fail(result.errors)
                productRepoDone = productRepoRead
            }
            is DbProductResponseErrWithData -> {
                fail(result.errors)
                productRepoDone = result.data
            }
        }
    }
}
