package org.sopt.santamanitto.network

data class Response<T>(
    val statusCode: Int,
    val message: String,
    val data: T
)