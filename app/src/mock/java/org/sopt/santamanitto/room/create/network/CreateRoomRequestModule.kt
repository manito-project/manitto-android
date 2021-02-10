package org.sopt.santamanitto.room.create.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
class CreateRoomRequestModule {

    @Provides
    fun provideFakeCreateRoomRequest(): CreateRoomRequest = FakeCreateRoomRequest()
}