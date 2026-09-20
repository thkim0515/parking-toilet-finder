package com.parkingtoiletfinder.app.data.repository

import com.parkingtoiletfinder.app.data.model.Place
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * 실제 공공데이터 API 연동 전까지 사용하는 더미 데이터 구현체.
 * 좌표/내용은 claude.ai/design 공영지도.dc.html 목업의 서울 중구 회현·명동 일대 샘플을 그대로 사용했다.
 */
@Singleton
class MockPlaceRepository @Inject constructor() : PlaceRepository {

    private val places: List<Place> = listOf(
        Place.Parking(
            id = "p1",
            name = "회현공영주차장",
            address = "서울 중구 회현동1가 100-1",
            latitude = 37.5590,
            longitude = 126.9784,
            totalSpots = 120,
            availableSpots = 34,
            feeDescription = "10분당 500원",
            operatingHours = "24시간 운영",
        ),
        Place.Parking(
            id = "p2",
            name = "명동주차타워",
            address = "서울 중구 명동2가 55",
            latitude = 37.5636,
            longitude = 126.9834,
            totalSpots = 80,
            availableSpots = 4,
            feeDescription = "10분당 700원",
            operatingHours = "07:00 - 24:00",
        ),
        Place.Parking(
            id = "p3",
            name = "남산공영주차장",
            address = "서울 중구 회현동 산1-2",
            latitude = 37.5580,
            longitude = 126.9810,
            totalSpots = 45,
            availableSpots = null,
            feeDescription = "정보 없음",
            operatingHours = "-",
        ),
        Place.Parking(
            id = "p4",
            name = "저동공영주차장",
            address = "서울 중구 저동2가 15",
            latitude = 37.5628,
            longitude = 126.9932,
            totalSpots = 30,
            availableSpots = 12,
            feeDescription = "10분당 600원",
            operatingHours = "06:00 - 22:00",
        ),
        Place.Restroom(
            id = "r1",
            name = "회현역 공중화장실",
            address = "서울 중구 회현동 지하철 2번출구",
            latitude = 37.5586,
            longitude = 126.9788,
            openingHours = "24시간 개방",
            hasAccessibleToilet = true,
        ),
        Place.Restroom(
            id = "r2",
            name = "명동광장 공중화장실",
            address = "서울 중구 명동8나길 13",
            latitude = 37.5633,
            longitude = 126.9865,
            openingHours = "06:00 - 22:00",
            hasAccessibleToilet = false,
        ),
    )

    override fun observePlaces(): Flow<List<Place>> = flowOf(places)

    override suspend fun getPlaceById(id: String): Place? = places.find { it.id == id }
}
