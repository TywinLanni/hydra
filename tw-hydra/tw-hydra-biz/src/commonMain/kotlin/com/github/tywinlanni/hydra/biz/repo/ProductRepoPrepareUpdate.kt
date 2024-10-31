package com.github.tywinlanni.hydra.biz.repo

import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.models.HydraState
import com.github.tywinlanni.libs.cor.dsl.ICorChainDsl
import com.github.tywinlanni.libs.cor.dsl.worker

fun ICorChainDsl<HydraProductContext>.repoPrepareUpdate(title: String) = worker {
    this.title = title
    description = "Готовим данные к сохранению в БД: совмещаем данные, прочитанные из БД, " +
            "и данные, полученные от пользователя"
    on { state == HydraState.RUNNING }
    handle {
        productRepoPrepare = productRepoRead.copy().apply {
            this.name = productValidating.name
            outerId = productValidating.outerId
            productType = productValidating.productType
            lock = productValidating.lock
            oneUnitWeightG = productValidating.oneUnitWeightG
            buyStep = productValidating.buyStep
            weighGtPerStorageLocation = productValidating.weighGtPerStorageLocation
            expirationDate = productValidating.expirationDate
            planedForPurchase = productValidating.planedForPurchase
        }
    }
}
