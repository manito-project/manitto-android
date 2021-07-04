package org.sopt.santamanitto.network

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class SimpleResponse(
    val status: Int,
    val success: Boolean,
    val message: String
)

fun Call<SimpleResponse>.start(callback: (Boolean) -> Unit) {
    val TAG = javaClass.simpleName + " response"
    enqueue(object : Callback<SimpleResponse> {
        override fun onResponse(call: Call<SimpleResponse>, response: Response<SimpleResponse>) {
            if (response.isSuccessful) {
                if (response.body() != null) {
                    if (response.body()!!.success) {
                        callback.invoke(true)
                    } else {
                        Log.e(TAG, "response body is not successful. ${response.body()!!.message}")
                        callback.invoke(false)
                    }
                } else {
                    Log.e(TAG, "response body is null. ${response.message()}")
                    callback.invoke(false)
                }
            } else {
                Log.e(TAG, "response is not successful. ${response.message()}")
                callback.invoke(false)
            }
        }

        override fun onFailure(call: Call<SimpleResponse>, t: Throwable) {
            Log.e(TAG, "request is fail. ${t.message}")
            callback.invoke(false)
        }
    })
}