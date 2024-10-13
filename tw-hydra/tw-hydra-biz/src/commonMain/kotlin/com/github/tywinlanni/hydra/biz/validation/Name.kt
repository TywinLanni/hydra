package com.github.tywinlanni.hydra.biz.validation

import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.helpers.errorValidation
import com.github.tywinlanni.hydra.common.helpers.fail
import com.github.tywinlanni.libs.cor.dsl.ICorChainDsl
import com.github.tywinlanni.libs.cor.dsl.chain
import com.github.tywinlanni.libs.cor.dsl.worker

fun ICorChainDsl<HydraProductContext>.validateName() = chain {
    worker {
        this.title = "Проверка валидности имени"

        on { productRequest.name.isBlank() }

        handle {
            fail(
                errorValidation(
                    field = "name",
                    violationCode = "empty or blank",
                    description = "field must not be empty"
                )
            )
        }
    }

    worker {
        this.title = "Проверка формата имени"

        on { productRequest.name.isNotBlank() && !productRequest.name.matches(Regex("^[a-zA-Z0-9\\s]+$")) }

        handle {
            fail(
                errorValidation(
                    field = "name",
                    violationCode = "badFormat",
                    description = "value ${productRequest.name} must contain only letters, numbers, and spaces"
                )
            )
        }
    }
}
