package com.github.tywinlanni.hydra.app.common

import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.models.HydraCommand
import com.github.tywinlanni.hydra.common.models.HydraState
import com.github.tywinlanni.hydra.common.helpers.asHydraError
import kotlinx.datetime.Clock

suspend inline fun <T> IHydraAppSettings.controllerHelper(
    crossinline getRequest: suspend HydraProductContext.() -> Unit,
    crossinline toResponse: suspend HydraProductContext.() -> T,
): T {
    val ctx = HydraProductContext(
        timeStart = Clock.System.now(),
    )
    return try {
        ctx.getRequest()

        processor.exec(ctx)

        ctx.toResponse()
    } catch (e: Throwable) {
        ctx.state = HydraState.FAILING

        ctx.errors.add(e.asHydraError())
        processor.exec(ctx)
        if (ctx.command == HydraCommand.NONE) {
            ctx.command = HydraCommand.READ
        }
        ctx.toResponse()
    }
}
