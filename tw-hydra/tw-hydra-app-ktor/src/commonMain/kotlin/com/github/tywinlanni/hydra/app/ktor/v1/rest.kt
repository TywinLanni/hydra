package com.github.tywinlanni.hydra.app.ktor.v1

import com.github.tywinlanni.hydra.app.ktor.HydraProductSettings
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.v1Product(appSettings: HydraProductSettings) {
    route("product") {
        post("create") {
            call.createProduct(appSettings)
        }
        post("read") {
            call.readProduct(appSettings)
        }
        post("update") {
            call.updateProduct(appSettings)
        }
        post("delete") {
            call.deleteProduct(appSettings)
        }
        post("search") {
            call.searchProduct(appSettings)
        }
    }
}
