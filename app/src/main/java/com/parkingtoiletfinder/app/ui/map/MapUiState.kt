package com.parkingtoiletfinder.app.ui.map

import com.parkingtoiletfinder.app.data.model.LatLng
import com.parkingtoiletfinder.app.data.model.PlaceFilter
import com.parkingtoiletfinder.app.domain.usecase.PlaceWithDistance

enum class MainViewMode { MAP, LIST }

data class MapUiState(
    val isLoading: Boolean = true,
    val places: List<PlaceWithDistance> = emptyList(),
    val filter: PlaceFilter = PlaceFilter.ALL,
    val viewMode: MainViewMode = MainViewMode.MAP,
    val currentLocation: LatLng = DEFAULT_LOCATION,
    val selectedPlaceId: String? = null,
) {
    companion object {
        // 목업 데이터 중심에 맞춘 기본 위치(서울 중구 회현동 일대). 실기기에서는 실제 GPS 위치로 대체 예정.
        val DEFAULT_LOCATION = LatLng(latitude = 37.5590, longitude = 126.9800)
    }
}
