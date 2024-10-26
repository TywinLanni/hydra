package com.github.tywinlanni.hydra.common.repo

import com.github.tywinlanni.hydra.common.helpers.errorSystem

abstract class ProductRepoBase: IRepoProduct {

    protected suspend fun tryProductMethod(block: suspend () -> IDbProductResponse) = try {
        block()
    } catch (e: Throwable) {
        DbProductResponseErr(errorSystem("methodException", e = e))
    }

    protected suspend fun tryProductsMethod(block: suspend () -> IDbProductsResponse) = try {
        block()
    } catch (e: Throwable) {
        DbProductsResponseErr(errorSystem("methodException", e = e))
    }

}
