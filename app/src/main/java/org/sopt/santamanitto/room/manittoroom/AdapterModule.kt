package org.sopt.santamanitto.room.manittoroom

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import org.sopt.santamanitto.user.data.source.UserDataSource
import javax.inject.Named

@Module
@InstallIn(FragmentComponent::class)
class AdapterModule {

    @Provides
    fun provideResultAdapter(@Named("cached") userDataSource: UserDataSource):
            ResultAdapter = ResultAdapter(userDataSource)
}