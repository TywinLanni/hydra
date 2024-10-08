package com.github.tywinlanni

import com.github.tywinlanni.models.*
import com.github.tywinlanni.stubs.HydraStubs
import kotlinx.datetime.Instant

data class HydraProductContext(
    var command: HydraCommand = HydraCommand.NONE,
    var state: HydraState = HydraState.NONE,
    val errors: MutableList<HydraError> = mutableListOf(),

    var workMode: HydraWorkMode = HydraWorkMode.PROD,
    var stubCase: HydraStubs = HydraStubs.NONE,

    var requestId: HydraRequestId = HydraRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var productRequest: HydraProduct = HydraProduct(),
    var productFilterRequest: HydraProductFilter = HydraProductFilter(),

    var productResponse: HydraProduct = HydraProduct(),
    var productsResponse: MutableList<HydraProduct> = mutableListOf(),
)