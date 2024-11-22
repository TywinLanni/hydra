package com.github.tywinlanni.hydra.common.repo

import com.github.tywinlanni.hydra.common.helpers.errorSystem
import com.github.tywinlanni.hydra.common.models.HydraError
import com.github.tywinlanni.hydra.common.models.HydraProduct
import com.github.tywinlanni.hydra.common.models.HydraProductId
import com.github.tywinlanni.hydra.common.models.HydraProductLock
import com.github.tywinlanni.hydra.common.repo.exceptions.RepoConcurrencyException
import com.github.tywinlanni.hydra.common.repo.exceptions.RepoException

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

fun errorRepoConcurrency(
    oldProduct: HydraProduct,
    expectedLock: HydraProductLock,
    exception: Exception = RepoConcurrencyException(
        id = oldProduct.id,
        expectedLock = expectedLock,
        actualLock = oldProduct.lock,
    ),
) = DbProductResponseErrWithData(
    product = oldProduct,
    err = HydraError(
        code = "$ERROR_GROUP_REPO-concurrency",
        group = ERROR_GROUP_REPO,
        field = "lock",
        message = "The object with ID ${oldProduct.id.asString()} has been changed concurrently by another user or process",
        exception = exception,
    )
)

fun errorEmptyLock(id: HydraProductId) = DbProductResponseErr(
    HydraError(
        code = "$ERROR_GROUP_REPO-lock-empty",
        group = ERROR_GROUP_REPO,
        field = "lock",
        message = "Lock for Product ${id.asString()} is empty that is not admitted"
    )
)

fun errorDb(e: RepoException) = DbProductResponseErr(
    errorSystem(
        violationCode = "dbLockEmpty",
        e = e
    )
)