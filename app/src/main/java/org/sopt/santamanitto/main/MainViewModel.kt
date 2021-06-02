package org.sopt.santamanitto.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.sopt.santamanitto.NetworkViewModel
import org.sopt.santamanitto.room.data.MyManitto
import org.sopt.santamanitto.user.data.source.*

class MainViewModel @ViewModelInject constructor(
    private val cachedMainUserDataSource: CachedMainUserDataSource
) : NetworkViewModel() {

    private var _myManittoList = MutableLiveData<List<MyManitto>?>(null)
    val myManittoList: LiveData<List<MyManitto>?>
        get() = _myManittoList

    private var _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean>
        get() = _isRefreshing

    fun getMyManittoList() {
        _isRefreshing.value = cachedMainUserDataSource.isMyManittoDirty
        _isLoading.value = true
        cachedMainUserDataSource.getMyManittoList(object : MainUserDataSource.GetJoinedRoomsCallback {
            override fun onMyManittoListLoaded(myManittos: List<MyManitto>) {
                _isLoading.value = false
                _myManittoList.value = myManittos
            }

            override fun onDataNotAvailable() {
                _networkErrorOccur.value = true
            }
        })
    }

    fun refresh() {
        cachedMainUserDataSource.isMyManittoDirty = true
        getMyManittoList()
    }
}