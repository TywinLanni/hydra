package com.github.tywinlanni

data class HydraCorSettings(
    val name: String = "Default Cor",
) {
    companion object {
        val NONE = HydraCorSettings()
    }
}
