package com.github.tywinlanni.hydra.common.repo

import com.github.tywinlanni.hydra.common.models.HydraError
import com.github.tywinlanni.hydra.common.models.HydraProductId

const val ERROR_GROUP_REPO = "repo"

fun errorNotFound(id: HydraProductId) = DbProductResponseErr(
    HydraError(
        code = "$ERROR_GROUP_REPO-not-found",
        group = ERROR_GROUP_REPO,
        field = "id",
        message = "Object with ID: ${id.asString()} is not Found",
    )
)

val errorEmptyId = DbProductResponseErr(
    HydraError(
        code = "$ERROR_GROUP_REPO-empty-id",
        group = ERROR_GROUP_REPO,
        field = "id",
        message = "Id must not be null or blank"
    )
)
