package com.github.tywinlanni.hydra.app.ktor

import com.github.tywinlanni.hydra.common.HydraCorSettings
import com.github.tywinlanni.hydra.app.common.IHydraAppSettings
import com.github.tywinlanni.hydra.biz.HydraProductProcessor
import com.github.tywinlanni.hydra.repo.ProductRepoInMemory
import com.github.tywinlanni.hydra.repo.ProductRepoStub

data class HydraProductSettings(
    override val corSettings: HydraCorSettings = HydraCorSettings(
        repoTest = ProductRepoInMemory(),
        repoProd = ProductRepoInMemory(),
        repoStub = ProductRepoStub(),
    ),
    override val processor: HydraProductProcessor = HydraProductProcessor(corSettings),
) : IHydraAppSettings
