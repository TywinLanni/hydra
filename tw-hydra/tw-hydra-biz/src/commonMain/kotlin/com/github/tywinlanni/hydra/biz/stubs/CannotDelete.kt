package com.github.tywinlanni.hydra.biz.stubs

import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.helpers.fail
import com.github.tywinlanni.hydra.common.models.HydraError
import com.github.tywinlanni.hydra.common.models.HydraState
import com.github.tywinlanni.hydra.common.stubs.HydraStubs
import com.github.tywinlanni.libs.cor.dsl.ICorChainDsl
import com.github.tywinlanni.libs.cor.dsl.worker

fun ICorChainDsl<HydraProductContext>.stubCannotDelete(title: String) = worker {
    this.title = title
    this.description = """
        продукт нельзя удалить
    """.trimIndent()
    on { stubCase == HydraStubs.CANNOT_DELETE && state == HydraState.RUNNING }
    handle {
        fail(
            HydraError(
                group = "operation",
                code = "cannot-delete",
                message = "Cannot delete"
            )
        )
    }
}
