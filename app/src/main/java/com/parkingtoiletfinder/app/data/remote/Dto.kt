package com.parkingtoiletfinder.app.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * TODO: 실제 공공데이터포털 응답 스키마에 맞춰 필드를 채울 것.
 * 지금은 [PublicDataApiService] 가 컴파일되기 위한 최소 형태만 잡아둔 자리표시자(placeholder) DTO.
 */
@JsonClass(generateAdapter = true)
data class ParkingLotResponseDto(
    @Json(name = "items") val items: List<ParkingLotDto> = emptyList(),
)

@JsonClass(generateAdapter = true)
data class ParkingLotDto(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "address") val address: String,
    @Json(name = "latitude") val latitude: Double,
    @Json(name = "longitude") val longitude: Double,
    @Json(name = "totalSpots") val totalSpots: Int,
    @Json(name = "availableSpots") val availableSpots: Int?,
    @Json(name = "feeDescription") val feeDescription: String,
    @Json(name = "operatingHours") val operatingHours: String,
)

@JsonClass(generateAdapter = true)
data class RestroomResponseDto(
    @Json(name = "items") val items: List<RestroomDto> = emptyList(),
)

@JsonClass(generateAdapter = true)
data class RestroomDto(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "address") val address: String,
    @Json(name = "latitude") val latitude: Double,
    @Json(name = "longitude") val longitude: Double,
    @Json(name = "openingHours") val openingHours: String,
    @Json(name = "hasAccessibleToilet") val hasAccessibleToilet: Boolean,
)
