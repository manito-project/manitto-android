package org.sopt.santamanitto

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import org.sopt.santamanitto.BuildConfig.AMPLITUDE_KEY
import org.sopt.santamanitto.analytics.AmplitudeManager
import timber.log.Timber

@HiltAndroidApp
class SantaManittoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initTimber()
        initAmplitude()
        setLightModeOnly()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun initAmplitude() {
        AmplitudeManager.init(this, AMPLITUDE_KEY)
    }

    private fun setLightModeOnly() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}