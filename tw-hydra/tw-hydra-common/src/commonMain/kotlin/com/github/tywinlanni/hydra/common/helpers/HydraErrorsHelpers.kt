package com.github.tywinlanni.hydra.common.helpers

import com.github.tywinlanni.hydra.common.models.HydraError

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
