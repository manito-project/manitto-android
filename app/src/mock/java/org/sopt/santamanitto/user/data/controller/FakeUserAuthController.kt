package org.sopt.santamanitto.user.data.controller

import org.sopt.santamanitto.user.data.UserInfoModel

class FakeUserAuthController : UserAuthController {

    private val fakeUsers = HashMap<String, UserInfoModel>().apply {
        put("1", UserInfoModel(1, "fakeUser1"))
        put("2", UserInfoModel(2, "fakeUser2"))
        put("3", UserInfoModel(3, "fakeUser3"))
        put("4", UserInfoModel(4, "fakeUser4"))
        put("5", UserInfoModel(5, "fakeUser5"))
        put("6", UserInfoModel(6, "fakeUser6"))
    }

    override suspend fun changeUserName(
        newName: String,
    ): Result<Boolean> {
        return Result.success(true)
    }

    override fun getUserInfo(userId: String, callback: UserAuthController.GetUserInfoCallback) {
        if (fakeUsers.containsKey(userId)) {
            callback.onUserInfoLoaded(fakeUsers[userId]!!)
        } else {
            callback.onDataNotAvailable()
        }
    }

    override suspend fun withdraw(): Result<Unit> {
        return Result.success(Unit)
    }
}