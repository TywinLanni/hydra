package com.githib.tywinlanni.hydra

import com.github.tywinlanni.hydra.api.v1.models.IRequest
import com.github.tywinlanni.hydra.api.v1.models.IResponse
import kotlinx.serialization.json.Json

@Suppress("JSON_FORMAT_REDUNDANT_DEFAULT")
val apiV1Mapper = Json {
//    ignoreUnknownKeys = true
}

@Suppress("UNCHECKED_CAST", "unused")
fun <T : IRequest> apiV1RequestDeserialize(json: String) =
    apiV1Mapper.decodeFromString<IRequest>(json) as T

@Suppress("unused")
fun apiV1ResponseSerialize(obj: IResponse): String =
    apiV1Mapper.encodeToString(IResponse.serializer(), obj)

@Suppress("UNCHECKED_CAST", "unused")
fun <T : IResponse> apiV1ResponseDeserialize(json: String) =
    apiV1Mapper.decodeFromString<IResponse>(json) as T

@Suppress("unused")
fun apiV1RequestSerialize(obj: IRequest): String =
    apiV1Mapper.encodeToString(IRequest.serializer(), obj)
