package com.github.tywinlanni.hydra.biz.repo

import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.helpers.fail
import com.github.tywinlanni.hydra.common.models.HydraState
import com.github.tywinlanni.hydra.common.repo.DbProductIdRequest
import com.github.tywinlanni.hydra.common.repo.DbProductResponseErr
import com.github.tywinlanni.hydra.common.repo.DbProductResponseErrWithData
import com.github.tywinlanni.hydra.common.repo.DbProductResponseOk
import com.github.tywinlanni.libs.cor.dsl.ICorChainDsl
import com.github.tywinlanni.libs.cor.dsl.worker


fun ICorChainDsl<HydraProductContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Чтение продукта из БД"
    on { state == HydraState.RUNNING }
    handle {
        val request = DbProductIdRequest(
            id = productValidating.id
        )

        when(val result = productRepo.readProduct(request)) {
            is DbProductResponseOk -> productRepoRead = result.data
            is DbProductResponseErr -> fail(result.errors)
            is DbProductResponseErrWithData -> {
                fail(result.errors)
                productRepoRead = result.data
            }
        }
    }
}
