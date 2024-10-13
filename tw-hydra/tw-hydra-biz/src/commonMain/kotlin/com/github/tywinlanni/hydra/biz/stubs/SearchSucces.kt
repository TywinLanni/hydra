package com.github.tywinlanni.hydra.biz.stubs

import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.models.HydraState
import com.github.tywinlanni.hydra.common.stubs.HydraStubs
import com.github.tywinlanni.hydra.stubs.HydraProductStub
import com.github.tywinlanni.libs.cor.dsl.ICorChainDsl
import com.github.tywinlanni.libs.cor.dsl.worker

fun ICorChainDsl<HydraProductContext>.stubSearchSuccess(title: String) = worker {
    this.title = title
    this.description = """
        Кейс успеха для чтения продуктов
    """.trimIndent()
    on { stubCase == HydraStubs.SUCCESS && state == HydraState.RUNNING }
    handle {
        state = HydraState.FINISHING

        productsResponse.addAll(HydraProductStub.prepareSearchList(productFilterRequest.searchString, productFilterRequest.productType))
    }
}
