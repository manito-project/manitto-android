package org.sopt.santamanitto

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
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
        initializeAds()
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

    private fun initializeAds() {
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTestDeviceIds(listOf("1d5b257d-5941-451b-947b-6c7275f5a7ae"))
                .build()
        )

        MobileAds.initialize(this)
    }
}