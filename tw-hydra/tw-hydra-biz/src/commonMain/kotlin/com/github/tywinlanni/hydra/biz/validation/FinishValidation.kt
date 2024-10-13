package com.github.tywinlanni.hydra.biz.validation

import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.models.HydraState
import com.github.tywinlanni.libs.cor.dsl.ICorChainDsl
import com.github.tywinlanni.libs.cor.dsl.worker

fun ICorChainDsl<HydraProductContext>.finishProductValidation() = worker {
    this.title = "Успешное завершение процедуры валидации"
    on { state == HydraState.RUNNING }

    handle {
        productValidating = productRequest.copy()
    }
}

fun ICorChainDsl<HydraProductContext>.finishProductFilterValidation() = worker {
    this.title = "Успешное завершение процедуры валидации"
    on { state == HydraState.RUNNING }

    handle {
        productFilterValidating = productFilterRequest.copy()
    }
}
