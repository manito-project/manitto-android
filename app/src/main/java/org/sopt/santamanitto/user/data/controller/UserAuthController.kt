package org.sopt.santamanitto.user.data.controller

import org.sopt.santamanitto.user.data.UserInfoResponse

interface UserAuthController {

    interface GetUserInfoCallback {
        fun onUserInfoLoaded(userInfoResponse: UserInfoResponse)

        fun onDataNotAvailable()
    }

    fun changeUserName(userId: Int, newName: String, callback: (isSuccess: Boolean) -> Unit)

    fun getUserInfo(userId: Int, callback: GetUserInfoCallback)
}