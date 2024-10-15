package com.github.tywinlanni.hydra.app.common

import com.github.tywinlanni.hydra.common.HydraCorSettings
import com.github.tywinlanni.hydra.biz.HydraProductProcessor

interface IHydraAppSettings {
    val processor: HydraProductProcessor
    val corSettings: HydraCorSettings
}
