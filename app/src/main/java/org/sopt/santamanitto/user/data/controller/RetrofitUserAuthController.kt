package org.sopt.santamanitto.user.data.controller

import org.sopt.santamanitto.network.RequestCallback
import org.sopt.santamanitto.network.start
import org.sopt.santamanitto.user.data.UserInfoModel
import org.sopt.santamanitto.user.mypage.UserNameModel
import org.sopt.santamanitto.user.mypage.UserNameRequestModel
import org.sopt.santamanitto.user.network.UserAuthService

class RetrofitUserAuthController(private val userAuthService: UserAuthService) :
    UserAuthController {
    override fun changeUserName(
        userId: Int,
        newName: String,
        callback: (isSuccess: Boolean) -> Unit,
    ) {
        userAuthService.changeUserName(userId, UserNameRequestModel(newName))
            .start(
                object : RequestCallback<UserNameModel> {
                    override fun onSuccess(data: UserNameModel) {
                        callback.invoke(true)
                    }

                    override fun onFail() {
                        callback.invoke(false)
                    }
                },
            )
    }

    override fun getUserInfo(
        userId: Int,
        callback: UserAuthController.GetUserInfoCallback,
    ) {
        userAuthService.getUserInfo(userId).start(
            object : RequestCallback<UserInfoModel> {
                override fun onSuccess(data: UserInfoModel) {
                    callback.onUserInfoLoaded(data)
                }

                override fun onFail() {
                    callback.onDataNotAvailable()
                }
            },
        )
    }
}
