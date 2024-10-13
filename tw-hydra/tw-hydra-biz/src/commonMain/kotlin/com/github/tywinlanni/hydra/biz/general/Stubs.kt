package com.github.tywinlanni.hydra.biz.general

import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.models.HydraState
import com.github.tywinlanni.hydra.common.models.HydraWorkMode
import com.github.tywinlanni.libs.cor.dsl.ICorChainDsl
import com.github.tywinlanni.libs.cor.dsl.chain

fun ICorChainDsl<HydraProductContext>.stubs(block: ICorChainDsl<HydraProductContext>.() -> Unit) = chain {
    block()
    this.title = "Обработка стабов"
    on { workMode == HydraWorkMode.STUB && state == HydraState.RUNNING }
}
