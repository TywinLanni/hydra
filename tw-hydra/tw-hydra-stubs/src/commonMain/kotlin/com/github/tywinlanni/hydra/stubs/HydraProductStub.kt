package com.github.tywinlanni.hydra.stubs

import com.github.tywinlanni.hydra.common.NONE
import com.github.tywinlanni.hydra.common.models.HydraProduct
import com.github.tywinlanni.hydra.common.models.HydraProductId
import com.github.tywinlanni.hydra.common.models.HydraProductLock
import com.github.tywinlanni.hydra.common.models.HydraProductType
import kotlinx.datetime.Instant
import kotlin.random.Random

object HydraProductStub {
    private val NONE_HYDRA_PRODUCT = HydraProduct()

    fun get(): HydraProduct = Defaults.HYDRA_PRODUCT_AIR.copy()

    fun prepareProduct(block: HydraProduct.() -> Unit): HydraProduct = get().apply(block)

    fun prepareSearchList(filter: String, type: HydraProductType, count: Int = 6): List<HydraProduct> {
        return (1..count).map { generateProductWithFilterAndType(filter, type, it.toString()) }
    }

    private fun generateProductWithFilterAndType(filter: String, type: HydraProductType, id: String): HydraProduct =
        HydraProduct(
            id = HydraProductId("${filter.lowercase()}-$id"),
            name = "${type.name} - $filter $id",
            outerId = "${filter.lowercase()}-$id",
            productType = type,
            oneUnitWeightG = Random.nextInt(1, 100),
            buyStep = Random.nextInt(1, 10),
            weighGtPerStorageLocation = Random.nextInt(1, 50),
            expirationDate = Instant.NONE,
            planedForPurchase = Random.nextBoolean(),
            lock = HydraProductLock("")
        )
}
