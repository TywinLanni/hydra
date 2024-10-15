package com.github.tywinlanni.hydra.app.ktor.base

import com.githib.tywinlanni.hydra.api1.apiV1ResponseSerialize
import com.github.tywinlanni.hydra.api.v1.models.IResponse
import com.github.tywinlanni.hydra.common.ws.IHydraWsSession
import io.ktor.websocket.*

class KtorWsSession(
    private val session: WebSocketSession
) : IHydraWsSession {
    override suspend fun <T> send(obj: T) {
        require(obj is IResponse)
        session.send(Frame.Text(apiV1ResponseSerialize(obj)))
    }
}
