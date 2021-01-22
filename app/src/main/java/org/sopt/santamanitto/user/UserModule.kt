package org.sopt.santamanitto.user

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import org.sopt.santamanitto.preference.UserPreferenceManager
import org.sopt.santamanitto.user.network.UserService
import org.sopt.santamanitto.user.source.UserDataSource
import org.sopt.santamanitto.user.source.UserLocalDataSource
import org.sopt.santamanitto.user.source.UserRemoteDataSource
import org.sopt.santamanitto.user.source.UserRepository
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
    @Named("userRemoteDataSource")
    fun provideUserRemoteDataSource(
        userService: UserService,
        @Named("serialNumber") serialNumber: String
    ) : UserDataSource = UserRemoteDataSource(userService, serialNumber)

    @Provides
    @Named("userLocalDataSource")
    fun provideUserLocalDataSource(userPreferenceManager: UserPreferenceManager): UserDataSource =
        UserLocalDataSource(userPreferenceManager)

    @Provides
    @Singleton
    @Named("userRepository")
    fun provideUserRepository(
        @Named("userLocalDataSource") userLocalDataSource: UserDataSource,
        @Named("userRemoteDataSource") userRemoteDataSource: UserDataSource
    ) : UserDataSource = UserRepository(userLocalDataSource, userRemoteDataSource)
}