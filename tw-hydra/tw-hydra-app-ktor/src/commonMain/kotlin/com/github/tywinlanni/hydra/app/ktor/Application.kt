package com.github.tywinlanni.hydra.app.ktor

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.githib.tywinlanni.hydra.api1.apiV1Mapper
import com.github.tywinlanni.hydra.app.ktor.v1.v1Product
import com.github.tywinlanni.hydra.app.ktor.v1.wsHandler
import io.ktor.server.websocket.*

fun Application.module(
    appSettings: HydraProductSettings = HydraProductSettings()
) {
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowHeader("MyCustomHeader")
        allowCredentials = true
        anyHost()
    }

    install(WebSockets)

    routing {
        get("/") {
            call.respondText("Hello, world!")
        }

        route("v1") {
            install(ContentNegotiation) {
                json(apiV1Mapper)
            }
            v1Product(appSettings)

            webSocket("/ws") {
                wsHandler(appSettings)
            }
        }
    }
}
