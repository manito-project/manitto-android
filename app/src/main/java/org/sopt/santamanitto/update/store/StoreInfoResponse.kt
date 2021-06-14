package org.sopt.santamanitto.update.store

data class StoreInfoResponse(
    val cacheSeconds: Int,
    val label: String,
    val message: String,
    val schemaVersion: Int
)
