package com.github.tywinlanni.hydra.common

import com.github.tywinlanni.hydra.common.ws.IHydraWsSessionRepo

data class HydraCorSettings(
    val name: String = "Default Cor",
    val wsSessions: IHydraWsSessionRepo = IHydraWsSessionRepo.NONE,
) {
    companion object {
        val NONE = HydraCorSettings()
    }
}
