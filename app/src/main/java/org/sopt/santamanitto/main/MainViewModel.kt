package org.sopt.santamanitto.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sopt.santamanitto.data.JoinedRoom
import org.sopt.santamanitto.user.source.UserDataSource
import javax.inject.Named

class MainViewModel @ViewModelInject constructor(
    @Named("cached") private val userCachedDataSource: UserDataSource
) : ViewModel() {

    private var _joinedRooms = MutableLiveData<List<JoinedRoom>?>(null)
    val joinedRooms : LiveData<List<JoinedRoom>?>
        get() = _joinedRooms

    private var _isFailToGetJoinedRooms = MutableLiveData<Boolean>(false)
    private val isFailToGetJoinedRooms : LiveData<Boolean>
        get() = _isFailToGetJoinedRooms


    fun getJoinedRooms() {
        userCachedDataSource.getJoinedRoom(object: UserDataSource.GetJoinedRoomsCallback {
            override fun onJoinedRoomsLoaded(rooms: List<JoinedRoom>) {
                _joinedRooms.value = rooms
            }

            override fun onDataNotAvailable() {
                _isFailToGetJoinedRooms.value = true
            }
        })
    }
}