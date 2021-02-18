package org.sopt.santamanitto.room.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import org.sopt.santamanitto.network.AuthRetrofitClient
import retrofit2.Retrofit

@InstallIn(ActivityComponent::class)
@Module
class RoomRequestModule {

    @Provides
    fun provideCreateRoomRequest(roomService: RoomService, @AuthRetrofitClient retrofitClient: Retrofit): RoomRequest =
        RoomRequestImpl(roomService, retrofitClient)
}