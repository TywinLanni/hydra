package com.githib.tywinlanni.mappers

import com.github.tywinlanni.HydraProductContext
import com.github.tywinlanni.NONE
import com.github.tywinlanni.hydra.api.v1.models.*
import com.github.tywinlanni.models.*
import com.github.tywinlanni.stubs.HydraStubs
import kotlinx.datetime.Instant

private fun String?.toAdId() = this?.let { HydraProductId(it) } ?: HydraProductId.NONE
private fun String?.toAdLock() = this?.let { HydraProductLock(it) } ?: HydraProductLock.NONE

private fun ProductDebug?.transportToWorkMode(): HydraWorkMode = when (this?.mode) {
    ProductRequestDebugMode.PROD -> HydraWorkMode.PROD
    ProductRequestDebugMode.TEST -> HydraWorkMode.TEST
    ProductRequestDebugMode.STUB -> HydraWorkMode.STUB
    null -> HydraWorkMode.PROD
}

private fun ProductDebug?.transportToStubCase(): HydraStubs = when (this?.stub) {
    ProductRequestDebugStubs.SUCCESS -> HydraStubs.SUCCESS
    ProductRequestDebugStubs.NOT_FOUND -> HydraStubs.NOT_FOUND
    ProductRequestDebugStubs.BAD_ID -> HydraStubs.BAD_ID
    ProductRequestDebugStubs.BAD_TITLE -> HydraStubs.BAD_TITLE
    ProductRequestDebugStubs.BAD_DESCRIPTION -> HydraStubs.BAD_DESCRIPTION
    ProductRequestDebugStubs.BAD_VISIBILITY -> HydraStubs.BAD_VISIBILITY
    ProductRequestDebugStubs.CANNOT_DELETE -> HydraStubs.CANNOT_DELETE
    ProductRequestDebugStubs.BAD_SEARCH_STRING -> HydraStubs.BAD_SEARCH_STRING
    null -> HydraStubs.NONE
}

fun HydraProductContext.fromTransport(request: IRequest) = when (request) {
    is ProductCreateRequest -> fromTransport(request)
    is ProductReadRequest -> fromTransport(request)
    is ProductDeleteRequest -> fromTransport(request)
    is ProductUpdateRequest -> fromTransport(request)
    is ProductSearchRequest -> fromTransport(request)
}

fun HydraProductContext.fromTransport(request: ProductCreateRequest) {
    command = HydraCommand.CREATE
    productRequest = request.product.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun HydraProductContext.fromTransport(request: ProductUpdateRequest) {
    command = HydraCommand.UPDATE
    productRequest = request.product?.toInternal() ?: HydraProduct()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun HydraProductContext.fromTransport(request: ProductReadRequest) {
    command = HydraCommand.READ
    productRequest = request.product.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun HydraProductContext.fromTransport(request: ProductDeleteRequest) {
    command = HydraCommand.DELETE
    productRequest = request.product.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun HydraProductContext.fromTransport(request: ProductSearchRequest) {
    command = HydraCommand.SEARCH
    productFilterRequest = request.productFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ProductType?.toInternal() = when (this) {
    ProductType.QUANTITY -> HydraProductType.QUANTITY
    ProductType.WEIGHT -> HydraProductType.WEIGHT
    null -> HydraProductType.NONE
}

fun ProductCreateObject?.toInternal() = HydraProduct(
    name = this?.name ?: "",
    outerId = this?.outerId ?: "",
    oneUnitWeightG = this?.oneUnitWeightG ?: 0,
    buyStep = this?.buyStep ?: 0,
    weighGtPerStorageLocation = this?.weighGtPerStorageLocation ?: 0,
    expirationDate = this?.expirationDate?.let {Instant.parse(it)} ?: Instant.NONE,
    planedForPurchase = this?.planedForPurchase ?: false,
    productType = this?.productType.toInternal(),
)

fun ProductUpdateObject.toInternal() = HydraProduct(
    id = this.id.toAdId(),
    name = this.name ?: "",
    outerId = this.outerId ?: "",
    oneUnitWeightG = this.oneUnitWeightG ?: 0,
    buyStep = this.buyStep ?: 0,
    weighGtPerStorageLocation = this.weighGtPerStorageLocation ?: 0,
    expirationDate = this.expirationDate?.let {Instant.parse(it)} ?: Instant.NONE,
    planedForPurchase = this.planedForPurchase ?: false,
    productType = this.productType.toInternal(),
    lock = lock.toAdLock(),
)

fun ProductDeleteObject?.toInternal()= if (this != null) {
    HydraProduct(
        id = id.toAdId(),
        lock = lock.toAdLock(),
    )
} else {
    HydraProduct()
}

fun ProductReadObject?.toInternal() = if (this != null) {
    HydraProduct(id = id.toAdId())
} else {
    HydraProduct()
}

fun Boolean?.toPlanedForPurchase() = when(this) {
    true -> HydraProductPlanedForPurchase.ONLY_PLANNED
    false -> HydraProductPlanedForPurchase.ONLY_NOT_PLANNED
    null -> HydraProductPlanedForPurchase.NONE
}

fun ProductSearchFilter?.toInternal() = HydraProductFilter(
    searchString = this?.searchString ?: "",
    productType = this?.productType.toInternal(),
    planedForPurchase = this?.planedForPurchase.toPlanedForPurchase(),
)
