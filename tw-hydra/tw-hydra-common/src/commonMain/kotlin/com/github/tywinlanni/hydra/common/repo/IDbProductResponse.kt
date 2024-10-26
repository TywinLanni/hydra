package com.github.tywinlanni.hydra.common.repo

import com.github.tywinlanni.hydra.common.models.HydraError
import com.github.tywinlanni.hydra.common.models.HydraProduct

sealed interface IDbProductResponse: IDbResponse<HydraProduct>

data class DbProductResponseOk(
    val data: HydraProduct
): IDbProductResponse

@Suppress("unused")
data class DbProductResponseErr(
    val errors: List<HydraError> = emptyList()
): IDbProductResponse {
    constructor(err: HydraError): this(listOf(err))
}
