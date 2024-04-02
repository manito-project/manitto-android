package org.sopt.santamanitto.user.data.source

import org.sopt.santamanitto.room.data.MyManittoModel
import org.sopt.santamanitto.user.data.UserInfoModel
import org.sopt.santamanitto.user.data.controller.UserAuthController
import javax.inject.Inject

class CachedMainUserDataSource @Inject constructor(
    private val userMetadataSource: UserMetadataSource,
    private val userAuthController: UserAuthController
) : MainUserDataSource {

    var isMyManittoDirty = true

    private var myManittoModels: List<MyManittoModel>? = null

    override fun getMyManittoList(callback: MainUserDataSource.GetJoinedRoomsCallback) {
        if (isMyManittoDirty || myManittoModels == null) {
            userAuthController.getUserInfo(
                userMetadataSource.getUserId(),
                object : UserAuthController.GetUserInfoCallback {
                    override fun onUserInfoLoaded(userInfoModel: UserInfoModel) {
                        isMyManittoDirty = false
                        myManittoModels = userInfoModel.myManittoModels.reversed()
                        callback.onMyManittoListLoaded(myManittoModels!!)
                    }

                    override fun onDataNotAvailable() {
                        callback.onDataNotAvailable()
                    }
                })
        } else {
            callback.onMyManittoListLoaded(myManittoModels!!)
        }
    }
}