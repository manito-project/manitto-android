package org.sopt.santamanitto.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.sopt.santamanitto.NetworkViewModel
import org.sopt.santamanitto.room.data.MyManitto
import org.sopt.santamanitto.room.network.RoomRequest
import org.sopt.santamanitto.user.data.source.CachedMainUserDataSource
import org.sopt.santamanitto.user.data.source.MainUserDataSource
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val cachedMainUserDataSource: CachedMainUserDataSource,
    private val roomRequest: RoomRequest
) : NetworkViewModel() {

    private var _myManittoList = MutableLiveData<List<MyManitto>?>(null)
    val myManittoList: LiveData<List<MyManitto>?>
        get() = _myManittoList

    private var _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean>
        get() = _isRefreshing

    fun fetchMyManittoList() {
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

    fun exitRoom(roomId: Int) {
        roomRequest.exitRoom(roomId) {
            if (it) {
                refresh()
            } else {
                _networkErrorOccur.value = true
            }
        }
    }

    fun refresh() {
        cachedMainUserDataSource.isMyManittoDirty = true
        fetchMyManittoList()
    }
}