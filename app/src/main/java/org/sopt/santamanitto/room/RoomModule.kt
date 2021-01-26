package org.sopt.santamanitto.room

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import org.sopt.santamanitto.room.data.source.CachedRoomDataSource
import org.sopt.santamanitto.room.data.source.RoomDataSource
import org.sopt.santamanitto.room.network.RoomService
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class RoomModule {

    @Provides
    @Singleton
    @Named("cached")
    fun provideCachedRoomDataSource(
        @Named("remote") remoteRoomDataSource: RoomDataSource
    ): RoomDataSource = CachedRoomDataSource(remoteRoomDataSource)

}