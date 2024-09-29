package com.github.tywinlanni.models

import kotlin.jvm.JvmInline

@JvmInline
value class HydraRequestId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = HydraRequestId("")
    }
}
