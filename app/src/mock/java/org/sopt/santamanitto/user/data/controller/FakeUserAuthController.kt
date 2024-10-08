package org.sopt.santamanitto.user.data.controller

import org.sopt.santamanitto.room.data.MyManittoModel
import org.sopt.santamanitto.user.data.UserInfoModel

class FakeUserAuthController : UserAuthController {

    private val fakeJoinedRooms = mutableListOf<MyManittoModel>().apply {
        add(
            MyManittoModel(
                1, "fakeRoom1", "1", false, false,
                "2021-02-15 12:33:44", "2021-01-25 12:33:44"
            )
        )

        add(
            MyManittoModel(
                2, "fakeRoom2", "2", true, false,
                "2021-02-10 12:33:44", "2021-01-24 12:33:44"
            )
        )

        add(
            MyManittoModel(
                3, "fakeRoom3", "3", false, true,
                "2021-01-30 12:33:44", "2021-01-20 12:33:44"
            )
        )

        add(
            MyManittoModel(
                4, "fakeRoom4", "1", false, true,
                "2021-01-14 12:33:44", "2021-01-12 12:33:44"
            )
        )

        add(
            MyManittoModel(
                5, "fakeRoom5", "1", false, true,
                "2021-01-11 12:33:44", "2021-01-09 12:33:44"
            )
        )
    }

    private val fakeUsers = HashMap<String, UserInfoModel>().apply {
        put("1", UserInfoModel(1, "fakeUser1", fakeJoinedRooms))
        put("2", UserInfoModel(2, "fakeUser2", mutableListOf()))
        put("3", UserInfoModel(3, "fakeUser3", mutableListOf()))
        put("4", UserInfoModel(4, "fakeUser4", mutableListOf()))
        put("5", UserInfoModel(5, "fakeUser5", mutableListOf()))
        put("6", UserInfoModel(6, "fakeUser6", mutableListOf()))
    }

    override fun changeUserName(
        userId: String,
        newName: String,
        callback: (isSuccess: Boolean) -> Unit
    ) {
        callback.invoke(newName != "fail")
    }

    override fun getUserInfo(userId: String, callback: UserAuthController.GetUserInfoCallback) {
        if (fakeUsers.containsKey(userId)) {
            callback.onUserInfoLoaded(fakeUsers[userId]!!)
        } else {
            callback.onDataNotAvailable()
        }
    }
}