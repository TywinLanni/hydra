package com.github.tywinlanni.hydra.repo

import com.github.tywinlanni.hydra.common.models.HydraProduct
import com.github.tywinlanni.hydra.common.repo.IRepoProduct

interface IRepoProductInitializable: IRepoProduct {
    suspend fun save(products: Collection<HydraProduct>): Collection<HydraProduct>
}