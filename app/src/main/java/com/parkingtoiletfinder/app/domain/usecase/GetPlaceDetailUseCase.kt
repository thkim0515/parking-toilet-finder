package com.parkingtoiletfinder.app.domain.usecase

import com.parkingtoiletfinder.app.data.model.Place
import com.parkingtoiletfinder.app.data.repository.PlaceRepository
import javax.inject.Inject

class GetPlaceDetailUseCase @Inject constructor(
    private val repository: PlaceRepository,
) {
    suspend operator fun invoke(placeId: String): Place? = repository.getPlaceById(placeId)
}
