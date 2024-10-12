package com.github.tywinlanni

import com.github.tywinlanni.hydra.api.v1.models.*
import com.githib.tywinlanni.hydra.mappers.fromTransport
import com.githib.tywinlanni.hydra.mappers.toTransportProduct
import com.github.tywinlanni.hydra.app.common.IHydraAppSettings
import com.github.tywinlanni.hydra.app.common.controllerHelper
import com.github.tywinlanni.hydra.biz.HydraProductProcessor
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ControllerTest {
    private val request = ProductCreateRequest(
        product = ProductCreateObject(
            name = "Test"
        ),
        debug = ProductDebug(mode = ProductRequestDebugMode.STUB, stub = ProductRequestDebugStubs.SUCCESS)
    )

    private val appSettings: IHydraAppSettings = object : IHydraAppSettings {
        override val corSettings: HydraCorSettings = HydraCorSettings()
        override val processor: HydraProductProcessor = HydraProductProcessor(corSettings)
    }

    class TestApplicationCall(private val request: IRequest) {
        var res: IResponse? = null

        @Suppress("UNCHECKED_CAST")
        fun <T : IRequest> receive(): T = request as T
        fun respond(res: IResponse) {
            this.res = res
        }
    }

    private suspend fun TestApplicationCall.createProductKtor(appSettings: IHydraAppSettings) {
        val resp = appSettings.controllerHelper(
            { fromTransport(receive<ProductCreateRequest>()) },
            { toTransportProduct() },
        )
        respond(resp)
    }

    @Test
    fun ktorHelperTest() = runTest {
        val testApp = TestApplicationCall(request).apply { createProductKtor(appSettings) }
        val res = testApp.res as ProductCreateResponse
        assertEquals(ResponseResult.SUCCESS, res.result)
    }
}
