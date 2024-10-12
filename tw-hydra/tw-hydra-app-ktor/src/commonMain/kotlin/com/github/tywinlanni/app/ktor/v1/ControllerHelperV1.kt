package com.github.tywinlanni.app.ktor.v1

import com.githib.tywinlanni.hydra.mappers.fromTransport
import com.githib.tywinlanni.hydra.mappers.toTransportProduct
import com.github.tywinlanni.app.ktor.HydraProductSettings
import com.github.tywinlanni.hydra.api.v1.models.IRequest
import com.github.tywinlanni.hydra.api.v1.models.IResponse
import com.github.tywinlanni.hydra.app.common.controllerHelper
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

suspend inline fun <reified Q : IRequest, @Suppress("unused") reified R : IResponse> ApplicationCall.processV2(
    appSettings: HydraProductSettings,
) = appSettings.controllerHelper(
    {
        fromTransport(this@processV2.receive<Q>())
    },
    { this@processV2.respond(toTransportProduct() as R) },
)
