package org.sopt.santamanitto.main

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import org.sopt.santamanitto.room.network.RoomRequest
import org.sopt.santamanitto.user.data.controller.UserAuthController

@InstallIn(FragmentComponent::class)
@Module
class AdapterModule {
    @Provides
    fun provideJoinedRoomAdapter(
        userAuthController: UserAuthController,
        roomRequest: RoomRequest
    ): MyManittoListAdapter = MyManittoListAdapter(userAuthController, roomRequest)
}