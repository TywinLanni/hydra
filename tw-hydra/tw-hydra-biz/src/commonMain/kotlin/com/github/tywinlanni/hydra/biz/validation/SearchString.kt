package com.github.tywinlanni.hydra.biz.validation

import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.helpers.errorValidation
import com.github.tywinlanni.hydra.common.helpers.fail
import com.github.tywinlanni.libs.cor.dsl.ICorChainDsl
import com.github.tywinlanni.libs.cor.dsl.chain
import com.github.tywinlanni.libs.cor.dsl.worker

const val MIN_SEARCH_STRING_LENGTH = 2
const val MAX_SEARCH_STRING_LENGTH = 30

fun ICorChainDsl<HydraProductContext>.validateSearchString() = chain {
    this.title = "Проверка валидности search string"

    worker {
        on { productFilterRequest.searchString.isBlank() }

        handle {
            fail(
                errorValidation(
                    field = "searchString",
                    violationCode = "empty or blank",
                    description = "field must not be empty"
                )
            )
        }
    }

    worker {
        on { productFilterRequest.searchString.length < MIN_SEARCH_STRING_LENGTH }

        handle {
            fail(
                errorValidation(
                    field = "searchString",
                    violationCode = "tooShort",
                    description = "field must be at least $MIN_SEARCH_STRING_LENGTH characters long"
                )
            )
        }
    }

    worker {
        on { productFilterRequest.searchString.length > MAX_SEARCH_STRING_LENGTH }

        handle {
            fail(
                errorValidation(
                    field = "searchString",
                    violationCode = "tooLong",
                    description = "field must not be longer than $MAX_SEARCH_STRING_LENGTH characters"
                )
            )
        }
    }
}
