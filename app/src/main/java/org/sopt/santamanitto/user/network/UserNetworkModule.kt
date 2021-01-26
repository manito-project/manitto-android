package org.sopt.santamanitto.user.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import org.sopt.santamanitto.network.OtherRetrofitClient
import retrofit2.Retrofit

@InstallIn(ApplicationComponent::class)
@Module
class UserNetworkModule {

    @Provides
    fun provideUserService(@OtherRetrofitClient retrofitClient: Retrofit): UserService =
        retrofitClient.create(UserService::class.java)
}