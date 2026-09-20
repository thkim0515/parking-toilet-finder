package com.parkingtoiletfinder.app.ui.map.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.parkingtoiletfinder.app.data.model.PlaceFilter
import com.parkingtoiletfinder.app.ui.theme.AppColors
import com.parkingtoiletfinder.app.ui.theme.AppShapes

/** 상단 검색바 + "내 주변" 배지. 실제 검색 입력/지오코딩은 지도 SDK 연동 후 채울 자리. */
@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(AppColors.Surface, AppShapes.Input)
            .border(1.dp, AppColors.Divider, AppShapes.Input)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = "검색",
            tint = AppColors.Neutral600,
            modifier = Modifier.size(18.dp),
        )
        Text(
            text = "내 주변에서 검색",
            style = MaterialTheme.typography.bodyMedium,
            color = AppColors.TextPrimary.copy(alpha = 0.6f),
            modifier = Modifier.weight(1f),
        )
        Box(
            modifier = Modifier
                .border(1.dp, AppColors.Accent, AppShapes.Pill)
                .padding(horizontal = 10.dp, vertical = 3.dp),
        ) {
            Text(
                text = "내 주변",
                style = MaterialTheme.typography.labelSmall,
                color = AppColors.Accent700,
            )
        }
    }
}

/** 전체 / 주차장 / 화장실 세그먼트 필터. */
@Composable
fun PlaceFilterSegment(
    selected: PlaceFilter,
    onSelect: (PlaceFilter) -> Unit,
    modifier: Modifier = Modifier,
) {
    val options = listOf(
        PlaceFilter.ALL to "전체",
        PlaceFilter.PARKING to "주차장",
        PlaceFilter.RESTROOM to "화장실",
    )
    Row(
        modifier = modifier
            .border(1.dp, AppColors.Divider, AppShapes.Pill),
    ) {
        options.forEach { (value, label) ->
            val isSelected = value == selected
            Box(
                modifier = Modifier
                    .background(if (isSelected) AppColors.Accent else Color.Transparent)
                    .clickable { onSelect(value) }
                    .padding(horizontal = 14.dp, vertical = 7.dp),
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isSelected) AppColors.Background else AppColors.TextPrimary,
                )
            }
        }
    }
}

@Composable
fun MapTopBar(
    filter: PlaceFilter,
    onFilterSelect: (PlaceFilter) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(AppColors.Background)
            .padding(PaddingValues(horizontal = 16.dp, vertical = 10.dp)),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        SearchBar()
        PlaceFilterSegment(selected = filter, onSelect = onFilterSelect)
    }
}
