package com.github.tywinlanni.hydra.common.models

data class HydraError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    //val level: LogLevel = LogLevel.ERROR,
    val exception: Throwable? = null,
)