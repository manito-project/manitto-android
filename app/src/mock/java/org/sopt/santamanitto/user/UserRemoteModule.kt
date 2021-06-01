package org.sopt.santamanitto.user

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import org.sopt.santamanitto.user.data.controller.FakeUserAuthController
import org.sopt.santamanitto.user.data.controller.FakeUserController
import org.sopt.santamanitto.user.data.controller.UserAuthController
import org.sopt.santamanitto.user.data.controller.UserController
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class UserRemoteModule {

    @Provides
    @Singleton
    fun provideUserAuthController(): UserAuthController = FakeUserAuthController()

    @Provides
    @Singleton
    fun provideUserController(): UserController = FakeUserController()
}