package org.sopt.santamanitto.user

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.santamanitto.user.data.controller.RetrofitUserAuthController
import org.sopt.santamanitto.user.data.controller.RetrofitUserController
import org.sopt.santamanitto.user.data.controller.UserAuthController
import org.sopt.santamanitto.user.data.controller.UserController
import org.sopt.santamanitto.user.network.UserAuthService
import org.sopt.santamanitto.user.network.UserService
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UserRemoteModule {

    @Provides
    @Singleton
    fun provideUserAuthController(userAuthService: UserAuthService): UserAuthController =
            RetrofitUserAuthController(userAuthService)

    @Provides
    @Singleton
    fun provideUserController(userService: UserService): UserController =
            RetrofitUserController(userService)
}