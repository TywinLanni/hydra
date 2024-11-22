package com.github.tywinlanni.hydra.common

import com.github.tywinlanni.hydra.common.repo.IRepoProduct
import com.github.tywinlanni.hydra.common.ws.IHydraWsSessionRepo

data class HydraCorSettings(
    val name: String = "Default Cor",
    val wsSessions: IHydraWsSessionRepo = IHydraWsSessionRepo.NONE,
    val repoStub: IRepoProduct = IRepoProduct.NONE,
    val repoTest: IRepoProduct = IRepoProduct.NONE,
    val repoProd: IRepoProduct = IRepoProduct.NONE,
) {
    companion object {
        val NONE = HydraCorSettings()
    }
}
