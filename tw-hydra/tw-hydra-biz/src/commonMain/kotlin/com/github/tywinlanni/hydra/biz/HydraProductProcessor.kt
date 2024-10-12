package com.github.tywinlanni.hydra.biz

import com.github.tywinlanni.HydraCorSettings
import com.github.tywinlanni.HydraProductContext
import com.github.tywinlanni.hydra.stubs.HydraProductStub
import com.github.tywinlanni.models.HydraProductType
import com.github.tywinlanni.models.HydraState

class HydraProductProcessor(val corSettings: HydraCorSettings) {
    suspend fun exec(ctx: HydraProductContext) {
        ctx.productResponse = HydraProductStub.get()
        ctx.productsResponse = HydraProductStub.prepareSearchList("product search", HydraProductType.WEIGHT).toMutableList()
        ctx.state = HydraState.RUNNING
    }
}
