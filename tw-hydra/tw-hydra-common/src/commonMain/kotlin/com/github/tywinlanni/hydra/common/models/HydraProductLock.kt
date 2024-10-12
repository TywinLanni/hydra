package com.github.tywinlanni.hydra.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class HydraProductLock(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = HydraProductLock("")
    }
}
