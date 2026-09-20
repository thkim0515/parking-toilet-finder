package com.parkingtoiletfinder.app.domain.usecase

import com.parkingtoiletfinder.app.data.model.LatLng
import com.parkingtoiletfinder.app.data.model.Place
import com.parkingtoiletfinder.app.data.model.PlaceFilter
import com.parkingtoiletfinder.app.data.model.distanceMeters
import com.parkingtoiletfinder.app.data.repository.PlaceRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/** 필터가 적용된 장소 목록을, 기준 좌표로부터의 거리 정보와 함께 내려준다. */
class GetNearbyPlacesUseCase @Inject constructor(
    private val repository: PlaceRepository,
) {
    operator fun invoke(
        origin: LatLng,
        filter: PlaceFilter,
    ): Flow<List<PlaceWithDistance>> {
        return repository.observePlaces().map { places ->
            places
                .filter { place ->
                    when (filter) {
                        PlaceFilter.ALL -> true
                        PlaceFilter.PARKING -> place is Place.Parking
                        PlaceFilter.RESTROOM -> place is Place.Restroom
                    }
                }
                .map { place ->
                    PlaceWithDistance(
                        place = place,
                        distanceMeters = distanceMeters(
                            from = origin,
                            to = LatLng(place.latitude, place.longitude),
                        ),
                    )
                }
                .sortedBy { it.distanceMeters }
        }
    }
}

data class PlaceWithDistance(
    val place: Place,
    val distanceMeters: Double,
)
