package com.github.tywinlanni.app.ktor

import com.github.tywinlanni.HydraCorSettings
import com.github.tywinlanni.hydra.app.common.IHydraAppSettings
import com.github.tywinlanni.hydra.biz.HydraProductProcessor

data class HydraProductSettings(
    override val corSettings: HydraCorSettings = HydraCorSettings(),
    override val processor: HydraProductProcessor = HydraProductProcessor(corSettings),
) : IHydraAppSettings