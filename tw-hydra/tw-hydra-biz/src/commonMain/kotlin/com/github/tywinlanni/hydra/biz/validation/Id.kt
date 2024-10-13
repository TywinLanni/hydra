package com.github.tywinlanni.hydra.biz.validation

import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.helpers.errorValidation
import com.github.tywinlanni.hydra.common.helpers.fail
import com.github.tywinlanni.libs.cor.dsl.ICorChainDsl
import com.github.tywinlanni.libs.cor.dsl.chain
import com.github.tywinlanni.libs.cor.dsl.worker

fun ICorChainDsl<HydraProductContext>.validateId() = chain {
    this.title = "Проверка валидности id"
    worker {
        on { productRequest.id.asString().isBlank() }

        handle {
            fail(
                errorValidation(
                    field = "id",
                    violationCode = "empty or blank",
                    description = "field must not be empty"
                )
            )
        }
    }

    worker {
        on { productRequest.id.asString().isNotBlank() && !productRequest.id.asString().matches(Regex("^[0-9a-zA-Z#:-]+$")) }

        handle {
            fail(
                errorValidation(
                    field = "id",
                    violationCode = "badFormat",
                    description = "value ${productRequest.id} must contain only letters and numbers"
                )
            )
        }
    }
}
