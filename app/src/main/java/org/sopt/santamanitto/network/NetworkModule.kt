package org.sopt.santamanitto.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.sopt.santamanitto.BuildConfig
import org.sopt.santamanitto.user.data.source.UserMetadataSource
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    @Singleton
    @OtherInterceptorOkHttpClient
    fun provideOtherInterceptorOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(provideLogger())
            .build()
    }

    @Provides
    @Singleton
    @AuthInterceptorOkHttpClient
    fun provideAuthInterceptorOkHttpClient(userMetadataSource: UserMetadataSource): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor {
                val request = it.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${userMetadataSource.getAccessToken()}")
                    .build()
                it.proceed(request)
            }
            .addInterceptor(provideLogger())
            .build()
    }

    @Provides
    @Singleton
    @OtherRetrofitClient
    fun provideOtherRetrofitClient(
        @OtherInterceptorOkHttpClient okHttpClient: OkHttpClient,
        baseUrl: String
    ): Retrofit =
        provideRetrofit(okHttpClient, baseUrl)

    @Provides
    @Singleton
    @AuthRetrofitClient
    fun provideAuthRetrofitClient(
        @AuthInterceptorOkHttpClient okHttpClient: OkHttpClient,
        baseUrl: String
    ): Retrofit =
        provideRetrofit(okHttpClient, baseUrl)

    private fun provideLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
}