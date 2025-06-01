package org.sopt.santamanitto.admob

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.lang.ref.WeakReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdmobInterstitialAdHelper @Inject constructor(
    @ApplicationContext private val context: Context,
) : InterstitialAdHelper {
    private var activityRef: WeakReference<Activity>? = null
    private var interstitialAd: InterstitialAd? = null
    private var isAdLoading = false
    private var adUnitId: String = ""

    override fun initialize(activity: Activity, adUnitId: String) {
        activityRef = WeakReference(activity)
        this.adUnitId = adUnitId
    }

    override fun loadAd() {
        if (isAdLoading) return
        isAdLoading = true

        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(context, adUnitId, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdLoaded(ad: InterstitialAd) {
                interstitialAd = ad
                isAdLoading = false
                setAdCallback()
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                interstitialAd = null
                isAdLoading = false
            }
        })
    }

    private fun setAdCallback() {
        interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                interstitialAd = null
                loadAd()
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                Timber.tag("admob").e(adError.message)
                interstitialAd = null
                loadAd()
            }

            override fun onAdShowedFullScreenContent() {
                interstitialAd = null
            }
        }
    }

    override fun showAdIfAvailable(onFinished: () -> Unit) {
        val ad = interstitialAd
        val activity = activityRef?.get()

        if (activity == null || activity.isFinishing) {
            onFinished()
            return
        }

        if (ad != null) {
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    interstitialAd = null
                    onFinished()
                    loadAd()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    Timber.tag("admob").e(adError.message)
                    interstitialAd = null
                    onFinished()
                }

                override fun onAdShowedFullScreenContent() {
                    interstitialAd = null
                }
            }
            ad.show(activity)
        } else {
            // 광고가 준비되지 않았으면 즉시 콜백만 실행
            onFinished()
            loadAd()
        }
    }
}