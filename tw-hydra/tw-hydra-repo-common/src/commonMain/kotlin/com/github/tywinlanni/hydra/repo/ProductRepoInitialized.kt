package com.github.tywinlanni.hydra.repo

import com.github.tywinlanni.hydra.common.models.HydraProduct
import kotlinx.coroutines.runBlocking

class ProductRepoInitialized(
    private val repo: IRepoProductInitializable,
    initObjects: Collection<HydraProduct> = emptyList(),
) : IRepoProductInitializable by repo {
    @Suppress("unused")
    val initializedObjects: List<HydraProduct> = runBlocking {
        save(initObjects).toList()
    }
}
