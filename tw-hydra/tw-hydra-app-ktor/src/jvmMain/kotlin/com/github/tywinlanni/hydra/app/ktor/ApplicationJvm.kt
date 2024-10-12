package com.github.tywinlanni.hydra.app.ktor

import com.github.tywinlanni.hydra.app.ktor.v1.wsHandler
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*

fun Application.moduleJvm(
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
            webSocket("/ws") {
                wsHandler(appSettings)
            }
        }
    }
}
