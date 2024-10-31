package com.github.tywinlanni.hydra.repo

import com.github.tywinlanni.hydra.common.models.HydraProduct
import com.github.tywinlanni.hydra.common.models.HydraProductId
import com.github.tywinlanni.hydra.common.models.HydraProductType

abstract class BaseInitProducts(private val op: String): com.github.tywinlanni.hydra.repo.IInitObjects<HydraProduct> {
    fun createInitTestModel(
        suf: String,
        outerId: String = "outer-123",
        productType: HydraProductType = HydraProductType.NONE,
    ) = HydraProduct(
        id = HydraProductId("product-repo-$op-$suf"),
        name = "$suf stub",
        outerId = outerId,
        productType = productType,
    )
}
