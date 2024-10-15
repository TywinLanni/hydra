package com.github.tywinlanni.hydra.biz.validation

import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.models.HydraState
import com.github.tywinlanni.libs.cor.dsl.ICorChainDsl
import com.github.tywinlanni.libs.cor.dsl.chain

fun ICorChainDsl<HydraProductContext>.validation(block: ICorChainDsl<HydraProductContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == HydraState.RUNNING }
}
