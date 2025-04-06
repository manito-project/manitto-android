package org.sopt.santamanitto.analytics

import android.content.Context
import com.amplitude.android.Amplitude
import com.amplitude.android.Configuration
import com.amplitude.android.events.Identify

object AmplitudeManager {
    private lateinit var amplitude: Amplitude

    fun init(
        context: Context,
        apiKey: String,
    ) {
        amplitude = Amplitude(
            Configuration(
                apiKey = apiKey,
                context = context.applicationContext,
            ),
        )
    }

    fun trackEvent(
        eventName: String,
        eventType: EventType
    ) {
        amplitude.track(eventName, mapOf(TYPE to eventType.type))
    }

    fun setUserId(userId: String) {
        amplitude.setUserId(userId)
    }

    fun updateStringProperty(
        propertyName: String,
        value: String,
    ) {
        amplitude.identify(Identify().set(propertyName, value))
    }

    private const val TYPE = "Type"
}
