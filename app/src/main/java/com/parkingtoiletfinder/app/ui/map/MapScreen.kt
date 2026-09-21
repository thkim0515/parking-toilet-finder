package com.parkingtoiletfinder.app.ui.map

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.parkingtoiletfinder.app.ui.common.launchNaverMapDirections
import com.parkingtoiletfinder.app.ui.detail.PlaceDetailBottomSheet
import com.parkingtoiletfinder.app.ui.list.PlaceListContent
import com.parkingtoiletfinder.app.ui.map.components.MapCanvasPlaceholder
import com.parkingtoiletfinder.app.ui.map.components.MapTopBar
import com.parkingtoiletfinder.app.ui.theme.AppColors
import com.parkingtoiletfinder.app.ui.theme.AppShapes

/**
 * 지도 메인 화면. 지도(또는 목록)는 상태바/네비게이션바까지 꽉 채우고(edge-to-edge),
 * 검색바/필터와 "목록 보기" 버튼은 그 위에 떠 있는 카드 형태로 배치한다(일반적인 지도 앱 스타일).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    viewModel: MapViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    Box(modifier = modifier.fillMaxSize().background(AppColors.Background)) {
        when (uiState.viewMode) {
            MainViewMode.MAP -> {
                MapCanvasPlaceholder(
                    places = uiState.places,
                    onMarkerClick = { viewModel.selectPlace(it) },
                    onMoveToCurrentLocation = viewModel::moveToCurrentLocation,
                    modifier = Modifier.fillMaxSize(),
                )
                MapTopBar(
                    filter = uiState.filter,
                    onFilterSelect = viewModel::setFilter,
                    modifier = Modifier.align(Alignment.TopCenter).statusBarsPadding(),
                )
                ShowListBar(
                    onClick = { viewModel.setViewMode(MainViewMode.LIST) },
                    modifier = Modifier.align(Alignment.BottomCenter).navigationBarsPadding(),
                )
            }
            MainViewMode.LIST -> Column(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.statusBarsPadding()) {
                    MapTopBar(filter = uiState.filter, onFilterSelect = viewModel::setFilter)
                }
                PlaceListContent(
                    places = uiState.places,
                    onPlaceClick = { viewModel.selectPlace(it) },
                    onNavigateClick = { launchNaverMapDirections(context, it.place) },
                    onShowMap = { viewModel.setViewMode(MainViewMode.MAP) },
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }

    val selected = uiState.places.find { it.place.id == uiState.selectedPlaceId }
    if (selected != null) {
        PlaceDetailBottomSheet(
            place = selected.place,
            distanceMeters = selected.distanceMeters,
            onDismiss = { viewModel.selectPlace(null) },
            onNavigateClick = { launchNaverMapDirections(context, selected.place) },
            sheetState = sheetState,
        )
    }
}

/** 지도 위에 떠 있는 "목록 보기" 알약 버튼 (일반 지도 앱의 지도/목록 전환 UI). */
@Composable
private fun ShowListBar(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(bottom = 16.dp)
            .shadow(elevation = 6.dp, shape = AppShapes.Pill, clip = false)
            .background(AppColors.TextPrimary, AppShapes.Pill)
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            Icons.Filled.List,
            contentDescription = null,
            tint = AppColors.Background,
            modifier = Modifier.padding(end = 6.dp),
        )
        Text(
            text = "목록 보기",
            style = MaterialTheme.typography.labelLarge,
            color = AppColors.Background,
        )
    }
}
