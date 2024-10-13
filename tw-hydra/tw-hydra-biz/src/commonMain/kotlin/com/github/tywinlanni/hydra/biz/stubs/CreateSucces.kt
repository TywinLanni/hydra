package com.github.tywinlanni.hydra.biz.stubs

import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.common.NONE
import com.github.tywinlanni.hydra.common.models.HydraProductType
import com.github.tywinlanni.hydra.common.models.HydraState
import com.github.tywinlanni.hydra.common.stubs.HydraStubs
import com.github.tywinlanni.hydra.stubs.HydraProductStub
import com.github.tywinlanni.libs.cor.dsl.ICorChainDsl
import com.github.tywinlanni.libs.cor.dsl.worker
import kotlinx.datetime.Instant

fun ICorChainDsl<HydraProductContext>.stubCreateSuccess(title: String) = worker {
    this.title = title
    this.description = """
        Кейс успеха для создания продукта
    """.trimIndent()
    on { stubCase == HydraStubs.SUCCESS && state == HydraState.RUNNING }

    handle {
        state = HydraState.FINISHING
        val stub = HydraProductStub.prepareProduct {
            productRequest.name.takeIf { it.isNotBlank() }?.let { this.name = it }
            productRequest.outerId.takeIf { it.isNotBlank() }?.let { this.outerId = it }
            productRequest.productType.takeIf { it != HydraProductType.NONE }?.let { this.productType = it }
            productRequest.oneUnitWeightG.takeIf { it != 0 }?.let { this.oneUnitWeightG = it }
            productRequest.buyStep.takeIf { it != 0 }?.let { this.buyStep = it }
            productRequest.weighGtPerStorageLocation.takeIf { it != 0 }?.let { this.weighGtPerStorageLocation = it }
            productRequest.expirationDate.takeIf { it != Instant.NONE }?.let { this.expirationDate = it }

            planedForPurchase = productRequest.planedForPurchase
        }

        productResponse = stub
    }
}
