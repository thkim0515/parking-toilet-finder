package com.parkingtoiletfinder.app.ui.map.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Wc
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.parkingtoiletfinder.app.data.model.ParkingStatus
import com.parkingtoiletfinder.app.data.model.Place
import com.parkingtoiletfinder.app.ui.theme.AppColors

/** 주차장 여유/혼잡/정보없음, 화장실 마커를 표시하는 원형 아이콘. 지도 마커와 목록 카드 리딩 아이콘에서 공유. */
@Composable
fun StatusMarker(
    place: Place,
    modifier: Modifier = Modifier,
    size: androidx.compose.ui.unit.Dp = 28.dp,
) {
    val (background, iconTint) = markerColors(place)
    Box(
        modifier = modifier
            .size(size)
            .background(background, CircleShape)
            .border(1.dp, AppColors.Background, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = if (place is Place.Restroom) Icons.Filled.Wc else Icons.Filled.DirectionsCar,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(size * 0.55f),
        )
    }
}

private fun markerColors(place: Place): Pair<Color, Color> = when (place) {
    is Place.Restroom -> AppColors.MarkerRestroom to AppColors.Background
    is Place.Parking -> when (place.status) {
        ParkingStatus.AVAILABLE -> AppColors.StatusGood to AppColors.Background
        ParkingStatus.CONGESTED -> AppColors.StatusBad to AppColors.Background
        ParkingStatus.UNKNOWN -> AppColors.MarkerNone to AppColors.MarkerNoneIcon
    }
}
