package com.github.tywinlanni.hydra.biz.stubs

import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.helpers.fail
import com.github.tywinlanni.hydra.common.models.HydraError
import com.github.tywinlanni.hydra.common.models.HydraState
import com.github.tywinlanni.hydra.common.stubs.HydraStubs
import com.github.tywinlanni.libs.cor.dsl.ICorChainDsl
import com.github.tywinlanni.libs.cor.dsl.worker

fun ICorChainDsl<HydraProductContext>.stubBadName(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки неверное имя
    """.trimIndent()
    on { stubCase == HydraStubs.BAD_NAME && state == HydraState.RUNNING }
    handle {
        fail(
            HydraError(
                group = "validation",
                code = "bad-name",
                message = "Invalid name"
            )
        )
    }
}
