package com.parkingtoiletfinder.app.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parkingtoiletfinder.app.data.model.PlaceFilter
import com.parkingtoiletfinder.app.domain.usecase.GetNearbyPlacesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getNearbyPlaces: GetNearbyPlacesUseCase,
) : ViewModel() {

    private val filter = MutableStateFlow(PlaceFilter.ALL)
    private val viewMode = MutableStateFlow(MainViewMode.MAP)
    private val selectedPlaceId = MutableStateFlow<String?>(null)
    private val currentLocation = MutableStateFlow(MapUiState.DEFAULT_LOCATION)

    val uiState: StateFlow<MapUiState> = combine(
        filter.flatMapLatest { f ->
            currentLocation.flatMapLatest { origin -> getNearbyPlaces(origin, f) }
        },
        filter,
        viewMode,
        selectedPlaceId,
        currentLocation,
    ) { places, filterValue, mode, selectedId, location ->
        MapUiState(
            isLoading = false,
            places = places,
            filter = filterValue,
            viewMode = mode,
            currentLocation = location,
            selectedPlaceId = selectedId,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MapUiState(),
    )

    fun setFilter(newFilter: PlaceFilter) {
        filter.value = newFilter
    }

    fun setViewMode(mode: MainViewMode) {
        viewMode.value = mode
    }

    fun selectPlace(placeId: String?) {
        selectedPlaceId.value = placeId
    }

    /** TODO: FusedLocationProviderClient 연동 시 실제 현재 위치로 교체. 지금은 기본 위치로 리셋만 수행. */
    fun moveToCurrentLocation() {
        currentLocation.update { MapUiState.DEFAULT_LOCATION }
    }
}
