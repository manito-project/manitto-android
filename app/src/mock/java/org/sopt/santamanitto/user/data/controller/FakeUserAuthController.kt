package org.sopt.santamanitto.user.data.controller

import org.sopt.santamanitto.room.data.JoinedRoom
import org.sopt.santamanitto.user.data.UserInfoResponse

class FakeUserAuthController: UserAuthController {

    private val fakeJoinedRooms = mutableListOf<JoinedRoom>().apply {
        add(
            JoinedRoom(1, "fakeRoom1", false,
            "2021-02-15 12:33:44", "2021-01-25 12:33:44")
        )

        add(
            JoinedRoom(2, "fakeRoom2", false,
            "2021-02-10 12:33:44", "2021-01-24 12:33:44")
        )

        add(
            JoinedRoom(3, "fakeRoom3", true,
            "2021-01-30 12:33:44", "2021-01-20 12:33:44")
        )

        add(
            JoinedRoom(4, "fakeRoom4", true,
            "2021-01-14 12:33:44", "2021-01-12 12:33:44")
        )

        add(
            JoinedRoom(5, "fakeRoom5", true,
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