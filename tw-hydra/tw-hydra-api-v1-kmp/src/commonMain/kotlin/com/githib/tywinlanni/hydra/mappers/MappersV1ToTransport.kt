package com.githib.tywinlanni.hydra.mappers

import com.github.tywinlanni.HydraProductContext
import com.github.tywinlanni.NONE
import com.github.tywinlanni.exceptions.UnknownHydraCommandException
import com.github.tywinlanni.hydra.api.v1.models.*
import com.github.tywinlanni.models.*
import kotlinx.datetime.Instant

fun HydraProductType.toTransport() = when (this) {
    HydraProductType.QUANTITY ->ProductType.QUANTITY
    HydraProductType.WEIGHT -> ProductType.WEIGHT
    HydraProductType.NONE -> null
}

fun HydraProductContext.toTransportProduct(): IResponse = when (val cmd = command) {
    HydraCommand.CREATE -> toTransportCreate()
    HydraCommand.READ -> toTransportRead()
    HydraCommand.UPDATE -> toTransportUpdate()
    HydraCommand.DELETE -> toTransportDelete()
    HydraCommand.SEARCH -> toTransportSearch()
    HydraCommand.FINISH -> throw UnknownHydraCommandException(cmd)
    HydraCommand.NONE -> throw UnknownHydraCommandException(cmd)
}


fun HydraProductContext.toTransportCreate() = ProductCreateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    product = productResponse.toTransportProduct()
)

fun HydraProductContext.toTransportRead() = ProductReadResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    product = productResponse.toTransportProduct()
)

fun HydraProductContext.toTransportUpdate() = ProductUpdateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    product = productResponse.toTransportProduct()
)

fun HydraProductContext.toTransportDelete() = ProductDeleteResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    product = productResponse.toTransportProduct()
)

fun HydraProductContext.toTransportSearch() = ProductSearchResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    products = productsResponse.toTransportProducts()
)

private fun HydraState.toResult(): ResponseResult? = when (this) {
    HydraState.RUNNING, HydraState.FINISHING -> ResponseResult.SUCCESS
    HydraState.FAILING -> ResponseResult.ERROR
    HydraState.NONE -> null
}

private fun List<HydraError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportProduct() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun HydraError.toTransportProduct() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)

fun List<HydraProduct>.toTransportProducts(): List<ProductResponseObject>? = this
    .map { it.toTransportProduct() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun HydraProduct.toTransportProduct(): ProductResponseObject = ProductResponseObject(
    id = id.takeIf { it != HydraProductId.NONE }?.asString(),
    outerId = outerId.takeIf { it.isNotBlank() },
    name = name.takeIf { it.isNotBlank() },
    oneUnitWeightG = oneUnitWeightG.takeIf { it != 0 },
    buyStep = buyStep.takeIf { it != 0 },
    weighGtPerStorageLocation = weighGtPerStorageLocation.takeIf { it != 0 },
    expirationDate = expirationDate.takeIf { it != Instant.NONE }?.toString(),
    planedForPurchase = planedForPurchase,
    productType = this.productType.toTransport(),
    lock = lock.takeIf { it != HydraProductLock.NONE }?.asString(),
)
