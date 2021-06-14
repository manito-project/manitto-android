package org.sopt.santamanitto.update

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import org.sopt.santamanitto.network.NetworkModule
import org.sopt.santamanitto.network.OtherInterceptorOkHttpClient
import org.sopt.santamanitto.update.store.StoreMetadataService
import org.sopt.santamanitto.update.version.RetrofitVersionChecker
import org.sopt.santamanitto.update.version.VersionChecker

@InstallIn(ApplicationComponent::class)
@Module
object UpdateModule {

    private const val VERSION_CHECK_URL = "https://playshields.herokuapp.com/"

    @Provides
    fun provideStoreMetadataService(@OtherInterceptorOkHttpClient okHttpClient: OkHttpClient): StoreMetadataService =
        NetworkModule.provideRetrofit(okHttpClient, VERSION_CHECK_URL).create(StoreMetadataService::class.java)

    @Provides
    fun provideVersionChecker(storeMetadataService: StoreMetadataService): VersionChecker =
        RetrofitVersionChecker(storeMetadataService)
}