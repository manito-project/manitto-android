package org.sopt.santamanitto.user.data.source

import org.sopt.santamanitto.room.data.MyManitto
import org.sopt.santamanitto.user.data.UserInfoResponse
import org.sopt.santamanitto.user.data.controller.UserAuthController

class CachedMainUserDataSource(
    private val userMetadataSource: UserMetadataSource,
    private val userAuthController: UserAuthController
) : MainUserDataSource {

    var isMyManittoDirty = true

    private var myManittos: List<MyManitto>? = null

    override fun getMyManittoList(callback: MainUserDataSource.GetJoinedRoomsCallback) {
        if (isMyManittoDirty || myManittos == null) {
            userAuthController.getUserInfo(
                userMetadataSource.getUserId(),
                object : UserAuthController.GetUserInfoCallback {
                    override fun onUserInfoLoaded(userInfoResponse: UserInfoResponse) {
                        isMyManittoDirty = false
                        myManittos = userInfoResponse.myManittos.reversed()
                        callback.onMyManittoListLoaded(myManittos!!)
                    }

                    override fun onDataNotAvailable() {
                        callback.onDataNotAvailable()
                    }
                })
        } else {
            callback.onMyManittoListLoaded(myManittos!!)
        }
    }
}