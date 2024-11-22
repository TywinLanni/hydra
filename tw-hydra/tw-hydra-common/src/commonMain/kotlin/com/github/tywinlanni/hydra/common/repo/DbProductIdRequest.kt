package com.github.tywinlanni.hydra.common.repo

import com.github.tywinlanni.hydra.common.models.HydraProduct
import com.github.tywinlanni.hydra.common.models.HydraProductId

data class DbProductIdRequest(
    val id: HydraProductId,
) {
    constructor(product: HydraProduct): this(product.id)
}
