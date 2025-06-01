package org.sopt.santamanitto.admob

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AdHelperModule {
    @Binds
    @Singleton
    abstract fun bindAdHelper(adHelperImpl: AdmobInterstitialAdHelper): InterstitialAdHelper
}