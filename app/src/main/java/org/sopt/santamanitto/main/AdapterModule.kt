package org.sopt.santamanitto.main

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import org.sopt.santamanitto.room.data.source.RoomDataSource
import org.sopt.santamanitto.room.network.RoomRequest
import org.sopt.santamanitto.user.data.source.UserDataSource
import javax.inject.Named

@InstallIn(FragmentComponent::class)
@Module
class AdapterModule {
    @Provides
    fun provideJoinedRoomAdapter(
        @Named("cached") cachedUserDataSource: UserDataSource,
        roomRequest: RoomRequest
    ): JoinedRoomsAdapter = JoinedRoomsAdapter(cachedUserDataSource, roomRequest)
}