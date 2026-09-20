package com.parkingtoiletfinder.app.di

import com.parkingtoiletfinder.app.BuildConfig
import com.parkingtoiletfinder.app.data.remote.PublicDataApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * TODO: [PublicDataApiService] 는 아직 실제 Repository 에서 사용되지 않는다 (Mock 데이터로 동작 중).
 * PUBLIC_DATA_API_KEY 가 채워지고 RemotePlaceRepository 구현체가 추가되면 이 모듈의 Retrofit 인스턴스를
 * 그대로 주입해서 쓰면 된다.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            }
        )
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.PUBLIC_DATA_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    @Singleton
    fun providePublicDataApiService(retrofit: Retrofit): PublicDataApiService =
        retrofit.create(PublicDataApiService::class.java)
}
