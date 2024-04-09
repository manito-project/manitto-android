package org.sopt.santamanitto.user.data.controller

import org.sopt.santamanitto.room.data.MyManittoModel
import org.sopt.santamanitto.user.data.UserInfoModel

class FakeUserAuthController: UserAuthController {

    private val fakeJoinedRooms = mutableListOf<MyManittoModel>().apply {
        add(
            MyManittoModel(1, "fakeRoom1", 1, false, false,
            "2021-02-15 12:33:44", "2021-01-25 12:33:44")
        )

        add(
            MyManittoModel(2, "fakeRoom2", 2, true, false,
            "2021-02-10 12:33:44", "2021-01-24 12:33:44")
        )

        add(
            MyManittoModel(3, "fakeRoom3", 3, false, true,
            "2021-01-30 12:33:44", "2021-01-20 12:33:44")
        )

        add(
            MyManittoModel(4, "fakeRoom4", 1, false, true,
            "2021-01-14 12:33:44", "2021-01-12 12:33:44")
        )

        add(
            MyManittoModel(5, "fakeRoom5", 1, false, true,
            "2021-01-11 12:33:44", "2021-01-09 12:33:44")
        )
    }

    private val fakeUsers = HashMap<Int, UserInfoModel>().apply {
        put(1, UserInfoModel(1, "fakeUser1", fakeJoinedRooms))
        put(2, UserInfoModel(2, "fakeUser2", mutableListOf()))
        put(3, UserInfoModel(3, "fakeUser3", mutableListOf()))
        put(4, UserInfoModel(4, "fakeUser4", mutableListOf()))
        put(5, UserInfoModel(5, "fakeUser5", mutableListOf()))
        put(6, UserInfoModel(6, "fakeUser6", mutableListOf()))
        put(7, UserInfoModel(7, "fakeUser7", mutableListOf()))
        put(8, UserInfoModel(8, "fakeUser8", mutableListOf()))
        put(9, UserInfoModel(9, "fakeUser9", mutableListOf()))
        put(10, UserInfoModel(10, "fakeUser10", mutableListOf()))
        put(11, UserInfoModel(11, "fakeUser11", mutableListOf()))
        put(12, UserInfoModel(12, "fakeUser12", mutableListOf()))
        put(13, UserInfoModel(13, "fakeUser13", mutableListOf()))
        put(14, UserInfoModel(14, "fakeUser14", mutableListOf()))
        put(15, UserInfoModel(15, "fakeUser15", mutableListOf()))
    }

    override fun changeUserName(
        userId: Int,
        newName: String,
        callback: (isSuccess: Boolean) -> Unit
    ) {
        callback.invoke(newName != "fail")
    }

    override fun getUserInfo(userId: Int, callback: UserAuthController.GetUserInfoCallback) {
        if (fakeUsers.containsKey(userId)) {
            callback.onUserInfoLoaded(fakeUsers[userId]!!)
        } else {
            callback.onDataNotAvailable()
        }
    }
}