package com.github.tywinlanni.hydra.common.models

data class HydraProductFilter(
    val searchString: String = "",
    val productType: HydraProductType = HydraProductType.NONE,
    val planedForPurchase: HydraProductPlanedForPurchase = HydraProductPlanedForPurchase.NONE,
)
