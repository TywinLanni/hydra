package com.github.tywinlanni.hydra.biz.stubs

import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.helpers.fail
import com.github.tywinlanni.hydra.common.models.HydraError
import com.github.tywinlanni.hydra.common.models.HydraState
import com.github.tywinlanni.hydra.common.stubs.HydraStubs
import com.github.tywinlanni.libs.cor.dsl.ICorChainDsl
import com.github.tywinlanni.libs.cor.dsl.worker


fun ICorChainDsl<HydraProductContext>.stubBadSearchString(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки неверная строка поиска
    """.trimIndent()
    on { stubCase == HydraStubs.BAD_SEARCH_STRING && state == HydraState.RUNNING }
    handle {
        fail(
            HydraError(
                group = "validation",
                code = "bad-search-string",
                message = "Invalid search string"
            )
        )
    }
}
