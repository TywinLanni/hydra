package com.github.tywinlanni.libs.cor.dsl

import com.github.tywinlanni.libs.cor.ICorExec
import com.github.tywinlanni.libs.cor.hendlers.CorWorker

@CorDslMarker
class CorWorkerDsl<T> : CorExecDsl<T>(), ICorWorkerDsl<T> {
    private var blockHandle: suspend T.() -> Unit = {}
    override fun handle(function: suspend T.() -> Unit) {
        blockHandle = function
    }

    override fun build(): ICorExec<T> = CorWorker(
        title = title,
        description = description,
        blockOn = blockOn,
        blockHandle = blockHandle,
        blockExcept = blockExcept
    )
}
