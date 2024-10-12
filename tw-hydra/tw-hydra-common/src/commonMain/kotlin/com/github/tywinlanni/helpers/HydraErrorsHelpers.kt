package com.github.tywinlanni.helpers

import com.github.tywinlanni.models.HydraError

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
