package org.sopt.santamanitto.room.create

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import org.sopt.santamanitto.room.network.CreateRoomRequest

@InstallIn(ActivityComponent::class)
@Module
class CreateRoomRequestModule {

    @Provides
    fun provideFakeCreateRoomRequest(): CreateRoomRequest = FakeCreateRoomRequest()
}