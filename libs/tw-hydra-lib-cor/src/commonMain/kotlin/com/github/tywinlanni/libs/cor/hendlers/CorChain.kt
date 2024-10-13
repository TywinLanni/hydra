package com.github.tywinlanni.libs.cor.hendlers

import com.github.tywinlanni.libs.cor.ICorExec

class CorChain<T>(
    private val execs: List<ICorExec<T>>,
    title: String,
    description: String = "",
    blockOn: suspend T.() -> Boolean = { true },
    blockExcept: suspend T.(Throwable) -> Unit = {},
) : AbstractCorExec<T>(title, description, blockOn, blockExcept) {
    override suspend fun handle(context: T) {
        execs.forEach {
            it.exec(context)
        }
    }
}
