package org.sopt.santamanitto.admob

import android.app.Activity

interface InterstitialAdHelper {
    fun initialize(activity: Activity, adUnitId: String)
    fun loadAd()

    /**
     * 광고가 준비되어 있으면 즉시 보여주고, 광고가 준비되지 않았거나 실패했으면 바로 onFinished 콜백만 호출한다.
     * @param onFinished: 광고가 다 닫혔거나, 아예 광고가 없을 때 실행할 콜백
     */
    fun showAdIfAvailable(onFinished: () -> Unit)
}