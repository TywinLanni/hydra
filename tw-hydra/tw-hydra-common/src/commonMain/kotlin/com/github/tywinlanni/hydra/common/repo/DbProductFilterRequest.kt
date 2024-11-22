package com.github.tywinlanni.hydra.common.repo

import com.github.tywinlanni.hydra.common.models.HydraProductPlanedForPurchase
import com.github.tywinlanni.hydra.common.models.HydraProductType

data class DbProductFilterRequest(
    val nameFilter: String = "",
    val productType: HydraProductType = HydraProductType.NONE,
    val planedForPurchase: HydraProductPlanedForPurchase = HydraProductPlanedForPurchase.NONE,
)
