package com.github.tywinlanni.hydra.common.repo

import com.github.tywinlanni.hydra.common.models.HydraError
import com.github.tywinlanni.hydra.common.models.HydraProduct

sealed interface IDbProductsResponse : IDbResponse<List<HydraProduct>>

data class DbProductsResponseOk(
    val data: List<HydraProduct>
) : IDbProductsResponse

@Suppress("unused")
data class DbProductsResponseErr(
    val errors: List<HydraError> = emptyList()
) : IDbProductsResponse {
    constructor(err: HydraError) : this(listOf(err))
}
