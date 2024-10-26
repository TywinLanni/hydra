package com.tywinlanni.hydra.repo

import com.github.tywinlanni.hydra.common.models.*
import com.github.tywinlanni.hydra.common.NONE
import kotlinx.datetime.Instant

data class ProductEntity(
    val id: String? = null,
    val name: String? = null,
    val outerId: String? = null,
    val productType: String? = null,
    val oneUnitWeightG: Int? = null,
    val buyStep: Int? = null,
    val weighGtPerStorageLocation: Int? = null,
    val expirationDate: String? = null,
    val planedForPurchase: Boolean? = null,
    val lock: String? = null,
) {
    constructor(model: HydraProduct): this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        name = model.name.takeIf { it.isNotBlank() },
        outerId = model.outerId.takeIf { it.isNotBlank() },
        productType = model.productType.takeIf { it != HydraProductType.NONE }?.name,
        oneUnitWeightG = model.oneUnitWeightG.takeIf { it != 0 },
        buyStep = model.buyStep.takeIf { it != 0 },
        weighGtPerStorageLocation = model.weighGtPerStorageLocation.takeIf { it != 0 },
        expirationDate = model.expirationDate.toString().takeIf { it.isNotBlank() },
        planedForPurchase = model.planedForPurchase,
        lock = model.lock.asString().takeIf { it.isNotBlank() }
    )

    fun toInternal() = HydraProduct(
        id = id?.let { HydraProductId(it) } ?: HydraProductId.NONE,
        name = name ?: "",
        outerId = outerId ?: "",
        productType = productType?.let { HydraProductType.valueOf(it) } ?: HydraProductType.NONE,
        oneUnitWeightG = oneUnitWeightG ?: 0,
        buyStep = buyStep ?: 0,
        weighGtPerStorageLocation = weighGtPerStorageLocation ?: 0,
        expirationDate = expirationDate?.let { Instant.parse(it) } ?: Instant.NONE,
        planedForPurchase = planedForPurchase ?: false,
        lock = lock?.let { HydraProductLock(it) } ?: HydraProductLock.NONE,
    )
}
