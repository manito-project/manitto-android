package org.sopt.santamanitto.user.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit

@InstallIn(ApplicationComponent::class)
@Module
class UserModule {

    @Provides
    fun provideUserService(retrofitClient: Retrofit): UserService =
        retrofitClient.create(UserService::class.java)
}