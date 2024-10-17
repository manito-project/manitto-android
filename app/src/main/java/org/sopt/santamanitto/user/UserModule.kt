package org.sopt.santamanitto.user

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.sopt.santamanitto.preference.UserPreferenceManager
import org.sopt.santamanitto.user.data.source.CachedUserMetadataSource
import org.sopt.santamanitto.user.data.source.UserMetadataSource
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UserModule {
    @SuppressLint("HardwareIds")
    @Provides
    @Named("serialNumber")
    fun provideSerialNumber(
        @ApplicationContext context: Context,
    ): String = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

    @Provides
    @Singleton
    fun provideCachedUserMetadataSource(userPreferenceManager: UserPreferenceManager): CachedUserMetadataSource =
        CachedUserMetadataSource(userPreferenceManager)

    @Provides
    @Singleton
    fun provideUserMetadataSource(userPreferenceManager: UserPreferenceManager): UserMetadataSource =
        CachedUserMetadataSource(userPreferenceManager)
}
