package com.parkingtoiletfinder.app.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessible
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.parkingtoiletfinder.app.data.model.ParkingStatus
import com.parkingtoiletfinder.app.data.model.Place
import com.parkingtoiletfinder.app.ui.list.etaCarMinutes
import com.parkingtoiletfinder.app.ui.list.etaWalkMinutes
import com.parkingtoiletfinder.app.ui.theme.AppColors
import com.parkingtoiletfinder.app.ui.theme.AppShapes

/**
 * 마커/카드를 탭했을 때 뜨는 상세 정보 바텀시트.
 * 주차장: 이름/주소/총면수/빈자리(또는 미제공)/요금/운영시간.
 * 화장실: 이름/주소/개방시간/장애인 화장실 여부.
 * 하단 "길 안내 시작" 버튼은 네이버 지도 앱으로 딥링크한다([launchNaverMapDirections] 참고).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceDetailBottomSheet(
    place: Place,
    distanceMeters: Double,
    onDismiss: () -> Unit,
    onNavigateClick: () -> Unit,
    sheetState: SheetState,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = AppColors.Background,
        shape = AppShapes.BottomSheet,
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
            ) {
                Column {
                    Text(text = place.name, style = MaterialTheme.typography.titleLarge)
                    Text(
                        text = place.address,
                        style = MaterialTheme.typography.labelMedium,
                        color = AppColors.TextPrimary.copy(alpha = 0.6f),
                    )
                }
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .border(1.dp, AppColors.Divider, CircleShape)
                        .clickable(onClick = onDismiss),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(Icons.Filled.Close, contentDescription = "닫기", modifier = Modifier.size(14.dp))
                }
            }

            when (place) {
                is Place.Parking -> ParkingDetailSection(place)
                is Place.Restroom -> RestroomDetailSection(place)
            }

            NavigateStartButton(distanceMeters = distanceMeters, onClick = onNavigateClick)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun ParkingDetailSection(place: Place.Parking) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppColors.Divider)
                .height(1.dp),
        )
        when (place.status) {
            ParkingStatus.UNKNOWN -> Column {
                Text(
                    text = "실시간 정보 미제공",
                    style = MaterialTheme.typography.titleMedium,
                    color = AppColors.Neutral600,
                )
                Text(
                    text = "총 ${place.totalSpots}면 · 위치만 확인 가능",
                    style = MaterialTheme.typography.labelSmall,
                    color = AppColors.TextPrimary.copy(alpha = 0.55f),
                )
            }
            ParkingStatus.AVAILABLE -> AvailabilityRow(place, AppColors.StatusGood, "")
            ParkingStatus.CONGESTED -> AvailabilityRow(place, AppColors.StatusBad, " · 혼잡")
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppColors.Divider)
                .height(1.dp),
        )
        Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            LabeledValue(label = "요금", value = place.feeDescription)
            LabeledValue(label = "운영시간", value = place.operatingHours)
        }
    }
}

@Composable
private fun AvailabilityRow(place: Place.Parking, color: androidx.compose.ui.graphics.Color, suffix: String) {
    Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "${place.availableSpots}",
            style = MaterialTheme.typography.headlineMedium,
            color = color,
        )
        Text(
            text = "면 여유 / 전체 ${place.totalSpots}면$suffix",
            style = MaterialTheme.typography.labelMedium,
            color = AppColors.TextPrimary.copy(alpha = 0.7f),
        )
    }
}

@Composable
private fun RestroomDetailSection(place: Place.Restroom) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Box(modifier = Modifier.fillMaxWidth().background(AppColors.Divider).height(1.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(20.dp), verticalAlignment = Alignment.CenterVertically) {
            LabeledValue(label = "개방시간", value = place.openingHours)
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Icon(
                    imageVector = Icons.Filled.Accessible,
                    contentDescription = null,
                    tint = AppColors.Accent700,
                    modifier = Modifier.size(16.dp),
                )
                Text(
                    text = if (place.hasAccessibleToilet) "장애인 화장실 있음" else "장애인 화장실 없음",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (place.hasAccessibleToilet) {
                        AppColors.TextPrimary
                    } else {
                        AppColors.TextPrimary.copy(alpha = 0.55f)
                    },
                )
            }
        }
        Box(modifier = Modifier.fillMaxWidth().background(AppColors.Divider).height(1.dp))
    }
}

@Composable
private fun LabeledValue(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = AppColors.TextPrimary.copy(alpha = 0.55f),
        )
        Text(text = value, style = MaterialTheme.typography.bodySmall, color = AppColors.TextPrimary)
    }
}

@Composable
private fun NavigateStartButton(distanceMeters: Double, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.Accent, AppShapes.Button)
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "길 안내 시작",
            style = MaterialTheme.typography.titleMedium,
            color = AppColors.Background,
        )
        Text(
            text = "도보 ${etaWalkMinutes(distanceMeters)}분 · 차량 ${etaCarMinutes(distanceMeters)}분",
            style = MaterialTheme.typography.labelSmall,
            color = AppColors.Background.copy(alpha = 0.85f),
        )
    }
}
