package org.sopt.santamanitto.update.store

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface StoreMetadataService {

    @GET("play")
    fun getStoreInfo(
        @Query("i") applicationId: String,
        @Query("l") platform: String,
        @Query("m") what: String
    ): Call<StoreInfoResponse>
}