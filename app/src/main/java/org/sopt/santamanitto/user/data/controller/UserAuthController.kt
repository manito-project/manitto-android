package org.sopt.santamanitto.user.data.controller

import org.sopt.santamanitto.user.data.UserInfoModel

interface UserAuthController {

    interface GetUserInfoCallback {
        fun onUserInfoLoaded(userInfoModel: UserInfoModel)

        fun onDataNotAvailable()
    }

    fun changeUserName(userId: String, newName: String, callback: (isSuccess: Boolean) -> Unit)

    fun getUserInfo(userId: String, callback: GetUserInfoCallback)
}