package com.github.tywinlanni.hydra.common

import com.github.tywinlanni.hydra.common.models.*
import com.github.tywinlanni.hydra.common.repo.IRepoProduct
import com.github.tywinlanni.hydra.common.stubs.HydraStubs
import com.github.tywinlanni.hydra.common.ws.IHydraWsSession
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

    var wsSession: IHydraWsSession = IHydraWsSession.NONE,

    var corSettings: HydraCorSettings = HydraCorSettings(),

    var productValidating: HydraProduct = HydraProduct(),
    var productFilterValidating: HydraProductFilter = HydraProductFilter(),

    var productRepo: IRepoProduct = IRepoProduct.NONE,
    var productRepoRead: HydraProduct = HydraProduct(), // То, что прочитали из репозитория
    var productRepoPrepare: HydraProduct = HydraProduct(), // То, что готовим для сохранения в БД
    var productRepoDone: HydraProduct = HydraProduct(), // Результат, полученный из БД
    var productsRepoDone: MutableList<HydraProduct> = mutableListOf(),
)
