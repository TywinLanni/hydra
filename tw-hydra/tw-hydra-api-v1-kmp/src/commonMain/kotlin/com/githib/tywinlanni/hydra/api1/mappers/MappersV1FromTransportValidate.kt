package com.githib.tywinlanni.hydra.api1.mappers

import com.github.tywinlanni.hydra.common.HydraProductContext
import com.github.tywinlanni.hydra.api.v1.models.ProductCreateRequest
import com.github.tywinlanni.hydra.common.models.HydraCommand
import com.github.tywinlanni.hydra.common.models.HydraError
import com.github.tywinlanni.hydra.common.models.HydraState
import com.github.tywinlanni.hydra.common.stubs.HydraStubs

private sealed interface Result<T,E>
private data class Ok<T,E>(val value: T) : Result<T, E>
private data class Err<T,E>(val errors: List<E>) : Result<T, E> {
    constructor(error: E) : this(listOf(error))
}

private fun <T,E> Result<T, E>.getOrExec(default: T, block: (Err<T, E>) -> Unit = {}): T = when (this) {
    is Ok<T, E> -> this.value
    is Err<T, E> -> {
        block(this)
        default
    }
}

@Suppress("unused")
private fun <T,E> Result<T, E>.getOrNull(block: (Err<T, E>) -> Unit = {}): T? = when (this) {
    is Ok<T, E> -> this.value
    is Err<T, E> -> {
        block(this)
        null
    }
}

private fun String?.transportToStubCaseValidated(): Result<HydraStubs, HydraError> = when (this) {
    "success" -> Ok(HydraStubs.SUCCESS)
    "notFound" -> Ok(HydraStubs.NOT_FOUND)
    "badId" -> Ok(HydraStubs.BAD_ID)
    "badTitle" -> Ok(HydraStubs.BAD_TITLE)
    "badDescription" -> Ok(HydraStubs.BAD_DESCRIPTION)
    "badVisibility" -> Ok(HydraStubs.BAD_VISIBILITY)
    "cannotDelete" -> Ok(HydraStubs.CANNOT_DELETE)
    "badSearchString" -> Ok(HydraStubs.BAD_SEARCH_STRING)
    null -> Ok(HydraStubs.NONE)
    else -> Err(
        HydraError(
            code = "wrong-stub-case",
            group = "mapper-validation",
            field = "debug.stub",
            message = "Unsupported value for case \"$this\""
        )
    )
}

@Suppress("unused")
fun HydraProductContext.fromTransportValidated(request: ProductCreateRequest) {
    command = HydraCommand.CREATE
    // Вся магия здесь!
    stubCase = request
        .debug
        ?.stub
        ?.value
        .transportToStubCaseValidated()
        .getOrExec(HydraStubs.NONE) { err: Err<HydraStubs, HydraError> ->
            errors.addAll(err.errors)
            state = HydraState.FAILING
        }
}
