package com.github.tywinlanni.hydra.stubs

import com.github.tywinlanni.models.HydraProduct
import com.github.tywinlanni.models.HydraProductId
import com.github.tywinlanni.models.HydraProductLock
import com.github.tywinlanni.models.HydraProductType
import kotlinx.datetime.Instant


object Defaults {
    // Define default values for HydraProduct
    val HYDRA_PRODUCT_AIR_NONE = HydraProduct()

    val HYDRA_PRODUCT_AIR: HydraProduct
        get() = HydraProduct(
            id = HydraProductId("air-req-1"),
            name = "Необходимый воздух",
            outerId = "air-req-1",
            productType = HydraProductType.WEIGHT,
            oneUnitWeightG = 1,
            buyStep = 1,
            weighGtPerStorageLocation = 1,
            expirationDate = Instant.fromEpochMilliseconds(0),
            planedForPurchase = true,
            lock = HydraProductLock("lock")
        )
}
