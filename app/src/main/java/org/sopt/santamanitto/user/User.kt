package org.sopt.santamanitto.user

import android.provider.Settings
import org.sopt.santamanitto.util.TimeUtil

data class User(
        val userName: String,
        val id: Int = 0,
        val serialNumber: String = Settings.Secure.ANDROID_ID,
        val updateAt: String = TimeUtil.getCurrentTime(),
        val createAt: String = TimeUtil.getCurrentTime(),
        val accessToken: String = "null"
)