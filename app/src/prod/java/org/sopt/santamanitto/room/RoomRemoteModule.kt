package org.sopt.santamanitto.room

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import org.sopt.santamanitto.room.data.source.RoomDataSource
import org.sopt.santamanitto.room.data.source.RoomRemoteDataSource
import org.sopt.santamanitto.room.create.network.RoomService
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class RoomRemoteModule {

    @Provides
    @Singleton
    @Named("remote")
    fun provideRoomRemoteDataSource(roomService: RoomService) : RoomDataSource =
        RoomRemoteDataSource(roomService)
}