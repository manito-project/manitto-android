package org.sopt.santamanitto.main

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import org.sopt.santamanitto.room.source.RoomDataSource
import javax.inject.Named

@InstallIn(FragmentComponent::class)
@Module
class AdapterModule {
    @Provides
    fun provideJoinedRoomAdapter(
        @Named("cached") cachedRoomDataSource: RoomDataSource
    ): JoinedRoomsAdapter = JoinedRoomsAdapter(cachedRoomDataSource)
}