package com.github.tywinlanni.hydra.biz

import com.github.tywinlanni.hydra.common.HydraCorSettings
import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.stubs.HydraProductStub
import com.github.tywinlanni.hydra.common.models.HydraProductType
import com.github.tywinlanni.hydra.common.models.HydraState

class HydraProductProcessor(val corSettings: HydraCorSettings) {
    suspend fun exec(ctx: HydraProductContext) {
        ctx.productResponse = HydraProductStub.get()
        ctx.productsResponse = HydraProductStub.prepareSearchList("product search", HydraProductType.WEIGHT).toMutableList()
        ctx.state = HydraState.RUNNING
    }
}
