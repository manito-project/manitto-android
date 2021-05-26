package org.sopt.santamanitto.user.data.source

import org.sopt.santamanitto.room.data.JoinedRoom
import org.sopt.santamanitto.user.data.UserInfoResponse
import org.sopt.santamanitto.user.data.controller.UserAuthController

class CachedMainUserDataSource(
    private val userMetadataSource: UserMetadataSource,
    private val userAuthController: UserAuthController
) : MainUserDataSource {

    var isJoinedRoomDirty = true

    private var joinedRooms: List<JoinedRoom>? = null

    override fun getJoinedRooms(callback: MainUserDataSource.GetJoinedRoomsCallback) {
        if (isJoinedRoomDirty || joinedRooms == null) {
            userAuthController.getUserInfo(
                userMetadataSource.getUserId(),
                object : UserAuthController.GetUserInfoCallback {
                    override fun onUserInfoLoaded(userInfoResponse: UserInfoResponse) {
                        isJoinedRoomDirty = false
                        joinedRooms = userInfoResponse.joinedRooms
                        callback.onJoinedRoomsLoaded(userInfoResponse.joinedRooms)
                    }

                    override fun onDataNotAvailable() {
                        callback.onDataNotAvailable()
                    }
                })
        } else {
            callback.onJoinedRoomsLoaded(joinedRooms!!)
        }
    }
}