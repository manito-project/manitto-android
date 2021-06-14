package org.sopt.santamanitto.update.version

import android.util.Log
import org.sopt.santamanitto.BuildConfig
import org.sopt.santamanitto.update.store.StoreInfoResponse
import org.sopt.santamanitto.update.store.StoreMetadataService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RetrofitVersionChecker(private val storeMetadataService: StoreMetadataService) :
    VersionChecker {

    companion object {
        private const val TAG = "RetrofitVersionChecker"
    }

    override fun getLatestVersion(callback: VersionChecker.GetLatestVersionCallback) {
        storeMetadataService
            .getStoreInfo(BuildConfig.APPLICATION_ID, "Android", "\$version")
            .enqueue(object : Callback<StoreInfoResponse> {
                override fun onResponse(
                    call: Call<StoreInfoResponse>,
                    response: Response<StoreInfoResponse>
                ) {
                    if (response.isSuccessful) {
                        val version = response.body()?.message
                        if (version == null) {
                            Log.e(TAG, "getLatestVersion(): message is null")
                            callback.onFailure(false)
                        }
                        callback.onLoadLatestVersion(response.body()!!.message)
                    } else {
                        Log.e(TAG, "getLatestVersion(): response is not successful.")
                        callback.onFailure(false)
                    }
                }

                override fun onFailure(call: Call<StoreInfoResponse>, t: Throwable) {
                    Log.d(TAG, "getLatestVersion(): error. ${t.message}")
                }
            })
    }
}