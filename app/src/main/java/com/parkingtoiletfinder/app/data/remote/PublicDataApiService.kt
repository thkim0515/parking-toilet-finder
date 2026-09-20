package com.parkingtoiletfinder.app.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * TODO: 공공데이터포털 "주차장정보" / "전국공중화장실표준데이터" API 연동용 인터페이스.
 * 현재는 [com.parkingtoiletfinder.app.data.repository.MockPlaceRepository] 만 DI로 주입되고 있어
 * 이 인터페이스는 아직 어디서도 호출되지 않는다. API 키(PUBLIC_DATA_API_KEY)가 발급되면
 * - 아래 엔드포인트 경로/파라미터를 실제 명세에 맞게 채우고
 * - RemotePlaceRepository 구현체를 추가한 뒤
 * - di/RepositoryModule 의 바인딩을 MockPlaceRepository -> RemotePlaceRepository 로 교체하면 된다.
 */
interface PublicDataApiService {

    @GET("placeholder/parking")
    suspend fun getParkingLots(
        @Query("serviceKey") serviceKey: String,
        @Query("pageNo") pageNo: Int = 1,
        @Query("numOfRows") numOfRows: Int = 100,
    ): ParkingLotResponseDto

    @GET("placeholder/restroom")
    suspend fun getPublicRestrooms(
        @Query("serviceKey") serviceKey: String,
        @Query("pageNo") pageNo: Int = 1,
        @Query("numOfRows") numOfRows: Int = 100,
    ): RestroomResponseDto
}
