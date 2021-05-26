package org.sopt.santamanitto.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.sopt.santamanitto.NetworkViewModel
import org.sopt.santamanitto.room.data.JoinedRoom
import org.sopt.santamanitto.user.data.source.*

class MainViewModel @ViewModelInject constructor(
    private val cachedMainUserDataSource: CachedMainUserDataSource
) : NetworkViewModel() {

    private var _joinedRooms = MutableLiveData<List<JoinedRoom>?>(null)
    val joinedRooms: LiveData<List<JoinedRoom>?>
        get() = _joinedRooms

    private var _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean>
        get() = _isRefreshing

    fun getJoinedRooms() {
        _isRefreshing.value = cachedMainUserDataSource.isJoinedRoomDirty
        _isLoading.value = true
        cachedMainUserDataSource.getJoinedRooms(object : MainUserDataSource.GetJoinedRoomsCallback {
            override fun onJoinedRoomsLoaded(joinedRooms: List<JoinedRoom>) {
                _isLoading.value = false
                _joinedRooms.value = joinedRooms
            }

            override fun onDataNotAvailable() {
                _networkErrorOccur.value = true
            }
        })
    }

    fun refresh() {
        cachedMainUserDataSource.isJoinedRoomDirty = true
        getJoinedRooms()
    }
}