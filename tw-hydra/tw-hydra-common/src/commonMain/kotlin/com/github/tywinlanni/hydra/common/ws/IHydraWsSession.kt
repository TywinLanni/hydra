package com.github.tywinlanni.hydra.common.ws

interface IHydraWsSession {
    suspend fun <T> send(obj: T)

    companion object {
        val NONE = object : IHydraWsSession {
            override suspend fun <T> send(obj: T) {}
        }
    }
}
