package org.sopt.santamanitto.network

import android.util.Log
import retrofit2.Call
import retrofit2.Callback

fun <T> Call<Response<T>>.start(callback: RequestCallback<T>) {
    val TAG = javaClass.simpleName + " response"
    enqueue(object: Callback<Response<T>> {
        override fun onResponse(
            call: Call<Response<T>>,
            response: retrofit2.Response<Response<T>>
        ) {
            if (response.isSuccessful) {
                if (response.body() != null) {
                    if (response.body()!!.success) {
                        callback.onSuccess(response.body()!!.data)
                    } else {
                        Log.e(TAG, "response body is not successful. ${response.body()!!.message}")
                        callback.onFail()
                    }
                } else {
                    Log.e(TAG, "response body is null. ${response.message()}")
                    callback.onFail()
                }
            } else {
                Log.e(TAG, "response is not successful. ${response.message()}")
                callback.onFail()
            }
        }

        override fun onFailure(call: Call<Response<T>>, t: Throwable) {
            Log.e(TAG, "request is fail. ${t.message}")
            callback.onFail()
        }
    })
}

interface RequestCallback<T> {
    fun onSuccess(data: T)

    fun onFail()
}