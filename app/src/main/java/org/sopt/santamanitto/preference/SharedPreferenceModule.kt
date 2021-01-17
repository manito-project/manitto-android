package org.sopt.santamanitto.preference

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import org.sopt.santamanitto.preference.SharedPreferenceManagerImpl.Companion.SHARED_PREFERENCES_FILE_NAME
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class SharedPreferenceModule {

    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences =
            context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideSharedPreferenceManager(sharedPreferences: SharedPreferences): SharedPreferenceManager =
            SharedPreferenceManagerImpl(sharedPreferences)

    @Provides
    fun provideUserPreferenceManager(sharedPreferenceManager: SharedPreferenceManager): UserPreferenceManager =
            UserPreferenceManager(sharedPreferenceManager)
}