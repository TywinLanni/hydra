package com.github.tywinlanni.hydra.biz.repo

import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.models.HydraState
import com.github.tywinlanni.libs.cor.dsl.ICorChainDsl
import com.github.tywinlanni.libs.cor.dsl.worker

fun ICorChainDsl<HydraProductContext>.repoPrepareDelete(title: String) = worker {
    this.title = title
    description = """
        Готовим данные к удалению из БД
    """.trimIndent()
    on { state == HydraState.RUNNING }
    handle {
        productRepoPrepare = productValidating.copy()
    }
}
