package com.tywinlanni.hydra.repo

import com.github.tywinlanni.hydra.common.models.HydraProduct

class ProductRepoInitialized(
    private val repo: IRepoProductInitializable,
    initObjects: Collection<HydraProduct> = emptyList(),
) : IRepoProductInitializable by repo {
    @Suppress("unused")
    val initializedObjects: List<HydraProduct> = save(initObjects).toList()
}
