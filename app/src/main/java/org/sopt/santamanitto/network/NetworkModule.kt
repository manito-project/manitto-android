package org.sopt.santamanitto.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.sopt.santamanitto.BuildConfig
import org.sopt.santamanitto.preference.UserPreferenceManager
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class NetworkModule {

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
    fun provideAuthInterceptorOkHttpClient(userPreferenceManager: UserPreferenceManager): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor {
                    val request = it.request().newBuilder()
                            .addHeader("jwt", userPreferenceManager.getAccessToken()!!)
                            .build()
                    it.proceed(request)
                }
                .addInterceptor(provideLogger())
                .build()
    }

    @Provides
    @Singleton
    @OtherRetrofitClient
    fun provideOtherRetrofitClient(@OtherInterceptorOkHttpClient okHttpClient: OkHttpClient, baseUrl: String): Retrofit =
            provideRetrofit(okHttpClient, baseUrl)

    @Provides
    @Singleton
    @AuthRetrofitClient
    fun provideAuthRetrofitClient(@AuthInterceptorOkHttpClient okHttpClient: OkHttpClient, baseUrl: String): Retrofit =
            provideRetrofit(okHttpClient, baseUrl)

    private fun provideLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
    }

    private fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit =
            Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .build()
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthInterceptorOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OtherInterceptorOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthRetrofitClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OtherRetrofitClient