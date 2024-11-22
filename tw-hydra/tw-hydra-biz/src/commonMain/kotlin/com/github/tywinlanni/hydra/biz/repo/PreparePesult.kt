package com.github.tywinlanni.hydra.biz.repo

import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.models.HydraState
import com.github.tywinlanni.hydra.common.models.HydraWorkMode
import com.github.tywinlanni.libs.cor.dsl.ICorChainDsl
import com.github.tywinlanni.libs.cor.dsl.worker

fun ICorChainDsl<HydraProductContext>.prepareResult(title: String) = worker {
    this.title = title
    description = "Подготовка данных для ответа клиенту на запрос"
    on { workMode != HydraWorkMode.STUB }
    handle {
        productResponse = productRepoDone
        productsResponse = productsRepoDone
        state = when (val st = state) {
            HydraState.RUNNING -> HydraState.FINISHING
            else -> st
        }
    }
}
