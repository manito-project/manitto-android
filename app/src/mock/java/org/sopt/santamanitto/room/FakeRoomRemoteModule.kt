package org.sopt.santamanitto.room

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.santamanitto.room.data.source.FakeRoomRemoteDataSource
import org.sopt.santamanitto.room.data.source.RoomDataSource
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class FakeRoomRemoteModule {

    @Provides
    @Singleton
    @Named("remote")
    fun provideRoomRemoteDataSource(): RoomDataSource = FakeRoomRemoteDataSource()
}