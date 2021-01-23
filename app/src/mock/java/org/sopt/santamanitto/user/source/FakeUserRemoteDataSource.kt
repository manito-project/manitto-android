package org.sopt.santamanitto.user.source

import org.sopt.santamanitto.data.JoinedRoom

class FakeUserRemoteDataSource : UserDataSource {

    private val fakeUser = User(
        "fakeUser",
        "f0a1k2e3u4e5s6e7r8",
        1,
        "face1access2token3"
    )

    private val fakeJoinedRooms = mutableListOf<JoinedRoom>().apply {
        add(JoinedRoom(1, "fakeRoom1", false,
            "2021-02-15 12:33:44", "2021-01-25 12:33:44"))

        add(JoinedRoom(2, "fakeRoom2", false,
            "2021-02-10 12:33:44", "2021-01-24 12:33:44"))

        add(JoinedRoom(3, "fakeRoom3", true,
            "2021-01-22 12:33:44", "2021-01-20 12:33:44"))

        add(JoinedRoom(4, "fakeRoom4", true,
            "2021-01-14 12:33:44", "2021-01-12 12:33:44"))

        add(JoinedRoom(5, "fakeRoom5", true,
            "2021-01-11 12:33:44", "2021-01-09 12:33:44"))

        add(JoinedRoom(6, "fakeRoom6", true,
            "2021-01-05 12:33:44", "2021-01-01 12:33:44"))
    }

    override fun login(serialNumber: String, callback: UserDataSource.LoginCallback) {
        callback.onLoginSuccess(fakeUser)
    }

    override fun createAccount(
        userName: String,
        serialNumber: String,
        callback: UserDataSource.CreateAccountCallback
    ) {
        callback.onCreateAccountSuccess(fakeUser)
    }

    override fun getUserId(): Int {
        return 0
    }

    override fun getAccessToken(): String {
        return ""
    }

    override fun getUserName(): String {
        return ""
    }

    override fun getJoinedRoom(userId: Int, callback: UserDataSource.GetJoinedRoomsCallback) {
        callback.onJoinedRoomsLoaded(fakeJoinedRooms)
    }
}