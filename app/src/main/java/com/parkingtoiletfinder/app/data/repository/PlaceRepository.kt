package com.parkingtoiletfinder.app.data.repository

import com.parkingtoiletfinder.app.data.model.Place
import kotlinx.coroutines.flow.Flow

/**
 * 주차장/화장실 데이터를 가져오는 공통 인터페이스.
 * 지금은 [MockPlaceRepository] 가 DI로 주입되고, 공공데이터 API 키가 준비되면
 * 이 인터페이스를 구현하는 실제 구현체(예: RemotePlaceRepository)로 교체만 하면 된다.
 */
interface PlaceRepository {
    fun observePlaces(): Flow<List<Place>>
    suspend fun getPlaceById(id: String): Place?
}
