package com.parkingtoiletfinder.app.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.parkingtoiletfinder.app.domain.usecase.PlaceWithDistance
import com.parkingtoiletfinder.app.ui.theme.AppColors

/** 근처 장소를 거리순으로 보여주는 카드 리스트 화면. */
@Composable
fun PlaceListContent(
    places: List<PlaceWithDistance>,
    onPlaceClick: (String) -> Unit,
    onNavigateClick: (PlaceWithDistance) -> Unit,
    onShowMap: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize().background(AppColors.Background)) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(places, key = { it.place.id }) { item ->
                PlaceCard(
                    item = item,
                    onClick = { onPlaceClick(item.place.id) },
                    onNavigateClick = { onNavigateClick(item) },
                )
            }
        }

        androidx.compose.foundation.layout.Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onShowMap)
                .navigationBarsPadding()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Filled.Map,
                contentDescription = null,
                tint = AppColors.Accent700,
                modifier = Modifier.padding(end = 6.dp),
            )
            Text(
                text = "지도에서 보기",
                style = MaterialTheme.typography.labelLarge,
                color = AppColors.Accent700,
            )
        }
    }
}
