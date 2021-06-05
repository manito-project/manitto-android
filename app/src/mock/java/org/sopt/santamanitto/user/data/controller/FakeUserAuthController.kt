package org.sopt.santamanitto.user.data.controller

import org.sopt.santamanitto.room.data.MyManitto
import org.sopt.santamanitto.user.data.UserInfoResponse

class FakeUserAuthController: UserAuthController {

    private val fakeJoinedRooms = mutableListOf<MyManitto>().apply {
        add(
            MyManitto(1, "fakeRoom1", 1, false,
            "2021-02-15 12:33:44", "2021-01-25 12:33:44")
        )

        add(
            MyManitto(2, "fakeRoom2", 2, false,
            "2021-02-10 12:33:44", "2021-01-24 12:33:44")
        )

        add(
            MyManitto(3, "fakeRoom3", 3, true,
            "2021-01-30 12:33:44", "2021-01-20 12:33:44")
        )

        add(
            MyManitto(4, "fakeRoom4", 1, true,
            "2021-01-14 12:33:44", "2021-01-12 12:33:44")
        )

        add(
            MyManitto(5, "fakeRoom5", 1, true,
            "2021-01-11 12:33:44", "2021-01-09 12:33:44")
        )
    }

    private val fakeUsers = HashMap<Int, UserInfoResponse>().apply {
        put(1, UserInfoResponse(1, "fakeUser1", fakeJoinedRooms))
        put(2, UserInfoResponse(2, "fakeUser2", mutableListOf()))
        put(3, UserInfoResponse(3, "fakeUser3", mutableListOf()))
        put(4, UserInfoResponse(4, "fakeUser4", mutableListOf()))
        put(5, UserInfoResponse(5, "fakeUser5", mutableListOf()))
        put(6, UserInfoResponse(6, "fakeUser6", mutableListOf()))
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