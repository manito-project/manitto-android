package org.sopt.santamanitto.room.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.santamanitto.network.AuthRetrofitClient
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RoomNetworkModule {

    @Provides
    @Singleton
    fun provideRoomService(@AuthRetrofitClient retrofitClient: Retrofit): RoomService =
        retrofitClient.create(RoomService::class.java)
}