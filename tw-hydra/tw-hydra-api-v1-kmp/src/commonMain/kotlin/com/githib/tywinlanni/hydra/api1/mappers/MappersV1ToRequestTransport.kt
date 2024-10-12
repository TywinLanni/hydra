package com.githib.tywinlanni.hydra.api1.mappers

import com.github.tywinlanni.hydra.common.NONE
import com.github.tywinlanni.hydra.api.v1.models.*
import com.github.tywinlanni.hydra.common.models.HydraProduct
import kotlinx.datetime.Instant

fun HydraProduct.toTransportCreate() = ProductCreateObject(
    name = name.takeIf { it.isNotBlank() },
    outerId = outerId.takeIf { it.isNotBlank() },
    oneUnitWeightG = oneUnitWeightG.takeIf { it != 0 },
    buyStep = buyStep.takeIf { it != 0 },
    weighGtPerStorageLocation = weighGtPerStorageLocation.takeIf { it != 0 },
    expirationDate = expirationDate.takeIf { it != Instant.NONE }?.toString(),
    planedForPurchase = planedForPurchase,
    productType = this.productType.toTransport(),
)

fun HydraProduct.toTransportRead() = ProductReadObject(
    id = id.takeIf { it != com.github.tywinlanni.hydra.common.models.HydraProductId.NONE }?.asString(),
)

fun HydraProduct.toTransportUpdate() = ProductUpdateObject(
    id = id.takeIf { it != com.github.tywinlanni.hydra.common.models.HydraProductId.NONE }?.asString(),
    name = name.takeIf { it.isNotBlank() },
    outerId = outerId.takeIf { it.isNotBlank() },
    oneUnitWeightG = oneUnitWeightG.takeIf { it != 0 },
    buyStep = buyStep.takeIf { it != 0 },
    weighGtPerStorageLocation = weighGtPerStorageLocation.takeIf { it != 0 },
    expirationDate = expirationDate.takeIf { it != Instant.NONE }?.toString(),
    planedForPurchase = planedForPurchase,
    productType = this.productType.toTransport(),
    lock = lock.takeIf { it != com.github.tywinlanni.hydra.common.models.HydraProductLock.NONE }?.asString(),
)

fun HydraProduct.toTransportDelete() = ProductDeleteObject(
    id = id.takeIf { it != com.github.tywinlanni.hydra.common.models.HydraProductId.NONE }?.asString(),
    lock = lock.takeIf { it != com.github.tywinlanni.hydra.common.models.HydraProductLock.NONE }?.asString(),
)
