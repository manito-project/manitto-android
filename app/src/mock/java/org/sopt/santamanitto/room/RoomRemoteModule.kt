package org.sopt.santamanitto.room

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import org.sopt.santamanitto.room.source.FakeRoomRemoteDataSource
import org.sopt.santamanitto.room.source.RoomDataSource
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class RoomRemoteModule {

    @Provides
    @Singleton
    @Named("remote")
    fun provideRoomRemoteDataSource(): RoomDataSource = FakeRoomRemoteDataSource()
}