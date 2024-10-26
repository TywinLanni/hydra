package com.github.tywinlanni.hydra.common.models

enum class HydraProductPlanedForPurchase {
    ONLY_PLANNED,
    ONLY_NOT_PLANNED,
    NONE;

    fun toBoolean() = when(this) {
        ONLY_PLANNED -> true
        ONLY_NOT_PLANNED -> false
        NONE -> null
    }
}
