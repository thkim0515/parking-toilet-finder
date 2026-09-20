package com.parkingtoiletfinder.app.di

import com.parkingtoiletfinder.app.data.repository.MockPlaceRepository
import com.parkingtoiletfinder.app.data.repository.PlaceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * 지금은 MockPlaceRepository 를 주입한다.
 * TODO: 공공데이터 API 연동 완료 시 RemotePlaceRepository 를 추가하고 아래 바인딩만 교체.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPlaceRepository(impl: MockPlaceRepository): PlaceRepository
}
