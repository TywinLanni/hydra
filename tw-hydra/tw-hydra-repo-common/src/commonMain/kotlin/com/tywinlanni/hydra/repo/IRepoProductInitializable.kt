package com.tywinlanni.hydra.repo

import com.github.tywinlanni.hydra.common.models.HydraProduct
import com.github.tywinlanni.hydra.common.repo.IRepoProduct

interface IRepoProductInitializable: IRepoProduct {
    fun save(products: Collection<HydraProduct>): Collection<HydraProduct>
}