package org.sopt.santamanitto.room.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
class FakeRoomRequestModule {

    @Provides
    fun provideFakeCreateRoomRequest(): RoomRequest = FakeRoomRequest()
}