package com.github.tywinlanni.models

import com.github.tywinlanni.NONE
import kotlinx.datetime.Instant

data class HydraProduct(
    var id: HydraProductId = HydraProductId.NONE,
    var name: String = "",
    var outerId: String = "",
    var productType: HydraProductType = HydraProductType.NONE,
    var oneUnitWeightG: Int = 0,
    var buyStep: Int = 0,
    var weighGtPerStorageLocation: Int = 0,
    var expirationDate: Instant = Instant.NONE,
    var planedForPurchase: Boolean = false,
    var lock: HydraProductLock = HydraProductLock.NONE,
)
