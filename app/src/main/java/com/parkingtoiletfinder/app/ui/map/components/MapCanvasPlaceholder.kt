package com.parkingtoiletfinder.app.ui.map.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.parkingtoiletfinder.app.domain.usecase.PlaceWithDistance
import com.parkingtoiletfinder.app.ui.theme.AppColors
import kotlin.math.abs

/**
 * 지도 SDK 자리에 들어가는 placeholder. 실제 지도(회색 그리드 + 건물 블록 느낌)를 흉내내고,
 * 장소는 좌표 대신 id 해시 기반 %좌표에 마커로 표시한다.
 *
 * TODO: 네이버 지도 Android SDK 연동 시 이 Composable을 NaverMap Compose 래퍼로 교체.
 *       (MapViewModel/MapUiState 등 상위 계층은 변경 없이 그대로 재사용 가능하도록 설계되어 있음)
 */
@Composable
fun MapCanvasPlaceholder(
    places: List<PlaceWithDistance>,
    onMarkerClick: (String) -> Unit,
    onMoveToCurrentLocation: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.background(AppColors.Neutral100)) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            // 건물 블록 느낌의 회색 사각형들 (지도 목업 재현)
            listOf(
                Triple(8f, 10f, 26f to 18f),
                Triple(60f, 8f, 32f to 14f),
                Triple(10f, 52f, 22f to 22f),
                Triple(64f, 66f, 28f to 20f),
            ).forEach { (x, y, wh) ->
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(x = maxWidth * (x / 100f), y = maxHeight * (y / 100f))
                        .size(width = maxWidth * (wh.first / 100f), height = maxHeight * (wh.second / 100f))
                        .background(AppColors.Neutral200, RoundedCornerShape(14.dp)),
                )
            }

            CurrentLocationDot(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = maxWidth * 0.47f, y = maxHeight * 0.88f),
            )

            places.forEach { item ->
                val (px, py) = markerPercentPosition(item.place.id)
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(x = maxWidth * (px / 100f), y = maxHeight * (py / 100f))
                        .clickable { onMarkerClick(item.place.id) },
                ) {
                    StatusMarker(place = item.place)
                }
            }
        }

        FloatingActionButton(
            onClick = onMoveToCurrentLocation,
            containerColor = AppColors.Background,
            contentColor = AppColors.TextPrimary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 14.dp, bottom = 78.dp)
                .size(44.dp)
                .border(1.dp, AppColors.Divider, CircleShape),
        ) {
            Icon(Icons.Filled.MyLocation, contentDescription = "현재 위치로 이동")
        }
    }
}

@Composable
private fun CurrentLocationDot(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "pulse")
    val scale by transition.animateFloat(
        initialValue = 1f,
        targetValue = 2.2f,
        animationSpec = infiniteRepeatable(tween(1600), RepeatMode.Restart),
        label = "pulseScale",
    )
    val alpha by transition.animateFloat(
        initialValue = 0.45f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(tween(1600), RepeatMode.Restart),
        label = "pulseAlpha",
    )
    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .scale(scale)
                .alpha(alpha)
                .background(AppColors.Accent, CircleShape),
        )
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(AppColors.Accent, CircleShape)
                .border(2.dp, AppColors.Background, CircleShape),
        )
    }
}

// claude.ai/design 공영지도.dc.html 목업 원본의 mapPlaces x/y(%) 값. 실제 위경도 투영 전까지의 임시 배치.
private val DESIGN_MOCK_POSITIONS = mapOf(
    "p1" to (30f to 26f),
    "p2" to (70f to 18f),
    "p3" to (52f to 62f),
    "p4" to (18f to 72f),
    "r1" to (40f to 44f),
    "r2" to (80f to 50f),
)

/** 디자인 목업에 없는 id는 해시 기반 15~85% 범위 좌표로 대체한다. */
private fun markerPercentPosition(id: String): Pair<Float, Float> {
    DESIGN_MOCK_POSITIONS[id]?.let { return it }
    val hash = abs(id.hashCode())
    val x = 15 + (hash % 70)
    val y = 15 + ((hash / 70) % 70)
    return x.toFloat() to y.toFloat()
}
