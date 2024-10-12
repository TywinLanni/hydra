package com.github.tywinlanni.hydra.app.ktor.base

import com.github.tywinlanni.hydra.common.ws.IHydraWsSession
import com.github.tywinlanni.hydra.common.ws.IHydraWsSessionRepo

class KtorWsSessionRepo: IHydraWsSessionRepo {
    private val sessions: MutableSet<IHydraWsSession> = mutableSetOf()
    override fun add(session: IHydraWsSession) {
        sessions.add(session)
    }

    override fun clearAll() {
        sessions.clear()
    }

    override fun remove(session: IHydraWsSession) {
        sessions.remove(session)
    }

    override suspend fun <T> sendAll(obj: T) {
        sessions.forEach { it.send(obj) }
    }
}
