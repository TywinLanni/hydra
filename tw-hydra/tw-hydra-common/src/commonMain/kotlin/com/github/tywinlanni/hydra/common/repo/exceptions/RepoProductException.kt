package com.github.tywinlanni.hydra.common.repo.exceptions

import com.github.tywinlanni.hydra.common.models.HydraProductId

open class RepoProductException(
    @Suppress("unused")
    val productId: HydraProductId,
    msg: String,
): RepoException(msg)
