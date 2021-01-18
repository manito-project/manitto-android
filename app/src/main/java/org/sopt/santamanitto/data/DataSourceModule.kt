package org.sopt.santamanitto.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DataSourceModule {

    @Provides
    @Singleton
    fun provideManittoDataSource() : ManittoDataSource = ManittoRemoteDataSource()
}