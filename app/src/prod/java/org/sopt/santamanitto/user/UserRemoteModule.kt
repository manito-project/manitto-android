package org.sopt.santamanitto.user

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import org.sopt.santamanitto.user.network.UserService
import org.sopt.santamanitto.user.source.UserDataSource
import org.sopt.santamanitto.user.source.UserRemoteDataSource
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class UserRemoteModule {

    @Provides
    @Singleton
    @Named("remote")
    fun provideUserRemoteDataSource(userService: UserService) : UserDataSource =
        UserRemoteDataSource(userService)
}