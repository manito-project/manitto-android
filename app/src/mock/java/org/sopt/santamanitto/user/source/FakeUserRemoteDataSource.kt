package org.sopt.santamanitto.user.source

import android.provider.Settings
import org.sopt.santamanitto.user.User
import org.sopt.santamanitto.util.TimeUtil
import java.lang.Exception

class FakeUserRemoteDataSource: UserDataSource {

    private val fakeUserData: HashMap<String, User> = LinkedHashMap()

    override fun getUser(callback: UserDataSource.GetUserCallback) {
        if (fakeUserData.containsKey(Settings.Secure.ANDROID_ID)) {
            callback.onUserLoaded(fakeUserData[Settings.Secure.ANDROID_ID]!!)
        } else {
            callback.onDataNotAvailable()
        }
    }

    override fun getUserId(callback: UserDataSource.GetUserIdCallback) {
        if (fakeUserData.containsKey(Settings.Secure.ANDROID_ID)) {
            callback.onUserIdLoaded(fakeUserData[Settings.Secure.ANDROID_ID]!!.id)
        } else {
            callback.onDataNotAvailable()
        }
    }

    override fun saveUser(user: User, callback: UserDataSource.SaveUserCallback?) {
        try {
            fakeUserData[user.serialNumber] = user
            callback?.onUserSaved(user)
        } catch (e: Exception) {
            callback?.onSaveFailed()
        }
    }

    override fun saveUser(name: String, callback: UserDataSource.SaveUserCallback?) {
        val fakeUser = User(name, 100, Settings.Secure.ANDROID_ID,
            "fake : ${TimeUtil.getCurrentTime()}",
            "fake : ${TimeUtil.getCurrentTime()}",
            "FAKExxYxMDg5MjgxNCwiaXNzIOdVrK0"
            )
        saveUser(fakeUser, callback)
    }
}