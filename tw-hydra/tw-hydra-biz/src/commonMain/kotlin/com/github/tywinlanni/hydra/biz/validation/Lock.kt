package com.github.tywinlanni.hydra.biz.validation

import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.helpers.errorValidation
import com.github.tywinlanni.hydra.common.helpers.fail
import com.github.tywinlanni.hydra.common.models.HydraProductLock
import com.github.tywinlanni.libs.cor.dsl.ICorChainDsl
import com.github.tywinlanni.libs.cor.dsl.chain
import com.github.tywinlanni.libs.cor.dsl.worker

fun ICorChainDsl<HydraProductContext>.validateLock() = chain {
    worker {
        this.title = "Проверка валидности lock"

        on { productRequest.lock != HydraProductLock.NONE && productRequest.lock.asString().isBlank() }

        handle {
            fail(
                errorValidation(
                    field = "lock",
                    violationCode = "empty or blank",
                    description = "field must not be empty"
                )
            )
        }
    }

    worker {
        this.title = "Проверка формата lock"

        on { productRequest.lock.asString().isNotBlank() && !productRequest.lock.asString().matches(Regex("^[0-9a-zA-Z-]+$")) }

        handle {
            fail(
                errorValidation(
                    field = "lock",
                    violationCode = "badFormat",
                    description = "value ${productRequest.lock} must contain only letters, numbers, and hyphens"
                )
            )
        }
    }
}
