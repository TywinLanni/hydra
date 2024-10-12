package com.github.tywinlanni.hydra.common.ws

interface IHydraWsSessionRepo {
    fun add(session: IHydraWsSession)
    fun clearAll()
    fun remove(session: IHydraWsSession)
    suspend fun <K> sendAll(obj: K)

    companion object {
        val NONE = object : IHydraWsSessionRepo {
            override fun add(session: IHydraWsSession) {}
            override fun clearAll() {}
            override fun remove(session: IHydraWsSession) {}
            override suspend fun <K> sendAll(obj: K) {}
        }
    }
}
