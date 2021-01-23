package org.sopt.santamanitto.user

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import org.sopt.santamanitto.user.network.UserService
import org.sopt.santamanitto.user.source.UserCachedDataSource
import org.sopt.santamanitto.user.source.UserDataSource
import org.sopt.santamanitto.user.source.UserRemoteDataSource
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class UserModule {

    @SuppressLint("HardwareIds")
    @Provides
    @Named("serialNumber")
    fun provideSerialNumber(@ApplicationContext context: Context) =
        Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

    @Provides
    @Singleton
    @Named("cached")
    fun provideUserDataSource(
        @Named("remote") userRemoteDataSource: UserDataSource,
        accessTokenContainer: AccessTokenContainer
    ) : UserDataSource = UserCachedDataSource(userRemoteDataSource, accessTokenContainer)

    @Provides
    @Singleton
    fun provideAccessToken() : AccessTokenContainer = AccessTokenContainer()
}