package com.github.tywinlanni.hydra.biz.stubs

import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.models.HydraProductId
import com.github.tywinlanni.hydra.common.models.HydraState
import com.github.tywinlanni.hydra.common.stubs.HydraStubs
import com.github.tywinlanni.hydra.stubs.HydraProductStub
import com.github.tywinlanni.libs.cor.dsl.ICorChainDsl
import com.github.tywinlanni.libs.cor.dsl.worker

fun ICorChainDsl<HydraProductContext>.stubReadSuccess(title: String) = worker {
    this.title = title
    this.description = """
        Кейс успеха для чтения продукта
    """.trimIndent()
    on { stubCase == HydraStubs.SUCCESS && state == HydraState.RUNNING }
    handle {
        state = HydraState.FINISHING

        val stub = HydraProductStub.prepareProduct {
            productRequest.id.takeIf { it != HydraProductId.NONE }?.also { this.id = it }
        }

        productResponse = stub
    }
}