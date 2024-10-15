package com.github.tywinlanni.hydra.app.ktor.v1

import com.github.tywinlanni.hydra.app.ktor.HydraProductSettings
import com.github.tywinlanni.hydra.api.v1.models.*
import io.ktor.server.application.*

suspend fun ApplicationCall.createProduct(appSettings: HydraProductSettings) =
    processV2<ProductCreateRequest, ProductCreateResponse>(appSettings)

suspend fun ApplicationCall.readProduct(appSettings: HydraProductSettings) =
    processV2<ProductReadRequest, ProductReadResponse>(appSettings)

suspend fun ApplicationCall.updateProduct(appSettings: HydraProductSettings) =
    processV2<ProductUpdateRequest, ProductUpdateResponse>(appSettings)

suspend fun ApplicationCall.deleteProduct(appSettings: HydraProductSettings) =
    processV2<ProductDeleteRequest, ProductDeleteResponse>(appSettings)

suspend fun ApplicationCall.searchProduct(appSettings: HydraProductSettings) =
    processV2<ProductSearchRequest, ProductSearchResponse>(appSettings)
