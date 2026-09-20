package com.parkingtoiletfinder.app.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.parkingtoiletfinder.app.data.model.ParkingStatus
import com.parkingtoiletfinder.app.data.model.Place
import com.parkingtoiletfinder.app.domain.usecase.PlaceWithDistance
import com.parkingtoiletfinder.app.ui.map.components.StatusMarker
import com.parkingtoiletfinder.app.ui.theme.AppColors
import com.parkingtoiletfinder.app.ui.theme.AppShapes
import kotlin.math.roundToInt

/** 근처 목록 화면의 카드 한 줄: 상태 아이콘 + 이름/거리/상태배지 + 안내 버튼. */
@Composable
fun PlaceCard(
    item: PlaceWithDistance,
    onClick: () -> Unit,
    onNavigateClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(AppColors.Surface, AppShapes.Card)
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        StatusMarker(place = item.place, size = 34.dp)

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.place.name,
                style = MaterialTheme.typography.titleMedium,
                color = AppColors.TextPrimary,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = formatDistance(item.distanceMeters),
                    style = MaterialTheme.typography.labelSmall,
                    color = AppColors.TextPrimary.copy(alpha = 0.5f),
                )
                StatusTag(place = item.place)
            }
        }

        Box(
            modifier = Modifier
                .size(36.dp)
                .border(1.dp, AppColors.Divider, CircleShape)
                .clickable(onClick = onNavigateClick),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "길 안내",
                tint = AppColors.Accent700,
                modifier = Modifier.size(16.dp),
            )
        }
    }
}

@Composable
private fun StatusTag(place: Place) {
    val (label, bg, fg) = when (place) {
        is Place.Restroom -> Triple("화장실", AppColors.Accent100, AppColors.Accent800)
        is Place.Parking -> when (place.status) {
            ParkingStatus.AVAILABLE -> Triple(
                "여유 ${place.availableSpots}면",
                AppColors.StatusGoodTint,
                AppColors.StatusGood,
            )
            ParkingStatus.CONGESTED -> Triple(
                "혼잡 ${place.availableSpots}면",
                AppColors.StatusBadTint,
                AppColors.StatusBad,
            )
            ParkingStatus.UNKNOWN -> Triple("정보 없음", AppColors.Neutral100, AppColors.Neutral800)
        }
    }
    Box(
        modifier = Modifier
            .background(bg, AppShapes.Pill)
            .padding(horizontal = 10.dp, vertical = 3.dp),
    ) {
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = fg)
    }
}

fun formatDistance(meters: Double): String =
    if (meters < 1000) "${meters.roundToInt()}m" else "%.1fkm".format(meters / 1000)

fun etaWalkMinutes(meters: Double): Int = maxOf(1, (meters / 70).roundToInt())

fun etaCarMinutes(meters: Double): Int = maxOf(1, (meters / 500).roundToInt())
