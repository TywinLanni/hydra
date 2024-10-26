package com.github.tywinlanni.hydra.common.helpers

import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.models.HydraError
import com.github.tywinlanni.hydra.common.models.HydraState

fun Throwable.asHydraError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = HydraError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

inline fun HydraProductContext.addError(vararg error: HydraError) = errors.addAll(error)

inline fun HydraProductContext.fail(error: HydraError) {
    addError(error)
    state = HydraState.FAILING
}

inline fun errorValidation(
    field: String,
    violationCode: String,
    description: String,
) = HydraError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
)

inline fun errorSystem(
    violationCode: String,
    e: Throwable,
) = HydraError(
    code = "system-$violationCode",
    group = "system",
    message = "System error occurred. Our stuff has been informed, please retry later",
    exception = e,
)
