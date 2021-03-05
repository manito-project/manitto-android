package org.sopt.santamanitto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class NetworkViewModel: ViewModel() {
    private var loadingCount = 0

    protected val _networkErrorOccur = MutableLiveData(false)
    val networkErrorOccur : LiveData<Boolean>
        get() = _networkErrorOccur

    protected val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    protected fun startLoading() {
        _isLoading.value = true
        loadingCount++
    }

    protected fun stopLoading() {
        loadingCount--
        if (loadingCount == 0) {
            _isLoading.value = false
        }
    }
}