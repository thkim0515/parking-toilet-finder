package com.parkingtoiletfinder.app.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

// 공영지도.dc.html 상단 <style> 오버라이드 값 그대로 (버튼/입력/다이얼로그 16, 카드 18, 태그/세그먼트는 pill)
object AppShapes {
    val Button = RoundedCornerShape(16.dp)
    val Input = RoundedCornerShape(16.dp)
    val Dialog = RoundedCornerShape(16.dp)
    val Card = RoundedCornerShape(18.dp)
    val Pill = RoundedCornerShape(999.dp)
    val BottomSheet = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
}
