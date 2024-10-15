package com.github.tywinlanni.hydra.biz.stubs

import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.helpers.fail
import com.github.tywinlanni.hydra.common.models.HydraError
import com.github.tywinlanni.hydra.common.models.HydraState
import com.github.tywinlanni.hydra.common.stubs.HydraStubs
import com.github.tywinlanni.libs.cor.dsl.ICorChainDsl
import com.github.tywinlanni.libs.cor.dsl.worker

fun ICorChainDsl<HydraProductContext>.stubNotFound(title: String) = worker {
    this.title = title
    this.description = """
        Кейс данные не найдены
    """.trimIndent()
    on { stubCase == HydraStubs.NOT_FOUND && state == HydraState.RUNNING }
    handle {
        fail(
            HydraError(
                group = "not-found",
                code = "not-found",
                message = "Not found"
            )
        )
    }
}
