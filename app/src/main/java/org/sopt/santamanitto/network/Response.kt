package org.sopt.santamanitto.network

data class Response<T>(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: T
)