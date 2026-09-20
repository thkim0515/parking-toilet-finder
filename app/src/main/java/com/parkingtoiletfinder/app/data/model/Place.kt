package com.parkingtoiletfinder.app.data.model

/** 지도/목록에 표시되는 장소(주차장 또는 화장실)의 공통 표현. */
sealed class Place {
    abstract val id: String
    abstract val name: String
    abstract val address: String
    abstract val latitude: Double
    abstract val longitude: Double

    data class Parking(
        override val id: String,
        override val name: String,
        override val address: String,
        override val latitude: Double,
        override val longitude: Double,
        val totalSpots: Int,
        /** null 이면 실시간 빈자리 정보 미제공. */
        val availableSpots: Int?,
        val feeDescription: String,
        val operatingHours: String,
    ) : Place() {
        val status: ParkingStatus
            get() = when {
                availableSpots == null -> ParkingStatus.UNKNOWN
                totalSpots <= 0 -> ParkingStatus.UNKNOWN
                availableSpots.toFloat() / totalSpots < CONGESTED_RATIO_THRESHOLD -> ParkingStatus.CONGESTED
                else -> ParkingStatus.AVAILABLE
            }

        private companion object {
            const val CONGESTED_RATIO_THRESHOLD = 0.15f
        }
    }

    data class Restroom(
        override val id: String,
        override val name: String,
        override val address: String,
        override val latitude: Double,
        override val longitude: Double,
        val openingHours: String,
        val hasAccessibleToilet: Boolean,
    ) : Place()
}

enum class ParkingStatus { AVAILABLE, CONGESTED, UNKNOWN }

enum class PlaceFilter { ALL, PARKING, RESTROOM }
