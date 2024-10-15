package com.github.tywinlanni.hydra.app.ktor.v1

import com.githib.tywinlanni.hydra.api1.apiV1RequestDeserialize
import com.githib.tywinlanni.hydra.api1.apiV1ResponseSerialize
import com.githib.tywinlanni.hydra.api1.mappers.fromTransport
import com.githib.tywinlanni.hydra.api1.mappers.toTransportInit
import com.githib.tywinlanni.hydra.api1.mappers.toTransportProduct
import com.github.tywinlanni.hydra.api.v1.models.IRequest
import com.github.tywinlanni.hydra.app.ktor.base.KtorWsSession
import com.github.tywinlanni.hydra.app.common.IHydraAppSettings
import com.github.tywinlanni.hydra.app.common.controllerHelper
import com.github.tywinlanni.hydra.common.models.HydraCommand
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.receiveAsFlow


suspend fun WebSocketSession.wsHandler(appSettings: IHydraAppSettings) = with(KtorWsSession(this)) {
    // Обновление реестра сессий
    val sessions = appSettings.corSettings.wsSessions
    sessions.add(this)

    // Handle init request
    appSettings.controllerHelper(
        {
            command = HydraCommand.INIT
            wsSession = this@with
        },
        { outgoing.send(Frame.Text(apiV1ResponseSerialize(toTransportInit()))) },
    )

    // Handle flow
    incoming.receiveAsFlow()
        .mapNotNull { message ->
            val frame = message as? Frame.Text ?: return@mapNotNull
            // Handle without flow destruction
            try {
                appSettings.controllerHelper(
                    {
                        fromTransport(apiV1RequestDeserialize<IRequest>(frame.readText()))
                        wsSession = this@with
                    },
                    {
                        val result = apiV1ResponseSerialize(toTransportProduct())
                        // If change request, response is sent to everyone
                        outgoing.send(Frame.Text(result))
                    },
                )

            } catch (_: ClosedReceiveChannelException) {
                sessions.remove(this@with)
            } catch (e: Throwable) {
                println("Sorry i'm dead")
            }
        }
        .onCompletion {
            // Handle finish request
            appSettings.controllerHelper(
                {
                    command = HydraCommand.FINISH
                    wsSession = this@with
                },
                { },
            )
            sessions.remove(this@with)
        }
        .collect()
}
