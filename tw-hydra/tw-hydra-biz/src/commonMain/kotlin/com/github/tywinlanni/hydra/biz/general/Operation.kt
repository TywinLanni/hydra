package com.github.tywinlanni.hydra.biz.general

import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.models.HydraCommand
import com.github.tywinlanni.hydra.common.models.HydraState
import com.github.tywinlanni.libs.cor.dsl.ICorChainDsl
import com.github.tywinlanni.libs.cor.dsl.chain

fun ICorChainDsl<HydraProductContext>.operation(
    title: String,
    command: HydraCommand,
    block: ICorChainDsl<HydraProductContext>.() -> Unit
) = chain {
    block()
    this.title = title
    on { this.command == command && state == HydraState.RUNNING }
}
