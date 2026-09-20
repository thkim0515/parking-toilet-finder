package com.parkingtoiletfinder.app.ui.map

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.parkingtoiletfinder.app.ui.common.launchNaverMapDirections
import com.parkingtoiletfinder.app.ui.detail.PlaceDetailBottomSheet
import com.parkingtoiletfinder.app.ui.list.PlaceListContent
import com.parkingtoiletfinder.app.ui.map.components.MapCanvasPlaceholder
import com.parkingtoiletfinder.app.ui.map.components.MapTopBar
import com.parkingtoiletfinder.app.ui.theme.AppColors

/** 지도 메인 화면: 검색바/필터 + 지도(또는 목록) + 상세 바텀시트. */
@Composable
fun MapScreen(
    viewModel: MapViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    Column(modifier = modifier.fillMaxSize().background(AppColors.Background)) {
        MapTopBar(filter = uiState.filter, onFilterSelect = viewModel::setFilter)

        Box(modifier = Modifier.weight(1f)) {
            when (uiState.viewMode) {
                MainViewMode.MAP -> Column(modifier = Modifier.fillMaxSize()) {
                    MapCanvasPlaceholder(
                        places = uiState.places,
                        onMarkerClick = { viewModel.selectPlace(it) },
                        onMoveToCurrentLocation = viewModel::moveToCurrentLocation,
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                    )
                    ShowListBar(onClick = { viewModel.setViewMode(MainViewMode.LIST) })
                }
                MainViewMode.LIST -> PlaceListContent(
                    places = uiState.places,
                    onPlaceClick = { viewModel.selectPlace(it) },
                    onNavigateClick = { launchNaverMapDirections(context, it.place) },
                    onShowMap = { viewModel.setViewMode(MainViewMode.MAP) },
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

@Composable
private fun ShowListBar(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.Background)
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(Icons.Filled.KeyboardArrowUp, contentDescription = null, tint = AppColors.TextPrimary)
        Text(
            text = "근처 목록 보기",
            style = MaterialTheme.typography.labelLarge,
            color = AppColors.TextPrimary,
            modifier = Modifier.padding(start = 4.dp),
        )
    }
}
