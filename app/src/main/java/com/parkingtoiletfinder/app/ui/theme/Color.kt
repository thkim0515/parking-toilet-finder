package com.parkingtoiletfinder.app.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * claude.ai/design "공영지도" 프로젝트(Industry 디자인 시스템)의 컬러 토큰을 그대로 옮긴 값.
 * 원본: _ds/industry-2fdf2218.../styles.css 의 :root 변수.
 */
object AppColors {
    // 기본 배경/표면/텍스트
    val Background = Color(0xFFF2F2F3)
    val Surface = Color(0xFFE9E9EA)
    val TextPrimary = Color(0xFF1D1F20)
    val Divider = Color(0x291D1F20) // color-mix(text 16%, transparent)

    // 포인트 컬러 (accent)
    val Accent = Color(0xFF5980A6)
    val Accent2 = Color(0xFF728FAB)

    val Accent100 = Color(0xFFEEF6FF)
    val Accent200 = Color(0xFFD6EBFF)
    val Accent300 = Color(0xFFB5D9FD)
    val Accent600 = Color(0xFF597EA3)
    val Accent700 = Color(0xFF416180)
    val Accent800 = Color(0xFF2C455D)
    val Accent900 = Color(0xFF1D2D3D)

    // 중립(뉴트럴) 톤 램프
    val Neutral100 = Color(0xFFF5F5F8)
    val Neutral200 = Color(0xFFE7E7EA)
    val Neutral300 = Color(0xFFD4D4D7)
    val Neutral400 = Color(0xFFB7B7BA)
    val Neutral500 = Color(0xFF98989B)
    val Neutral600 = Color(0xFF7A7A7D)
    val Neutral700 = Color(0xFF5D5D60)
    val Neutral800 = Color(0xFF424244)
    val Neutral900 = Color(0xFF2B2B2D)

    // 상태 색상: 주차장 여유/혼잡
    val StatusGood = Color(0xFF5F8F6A)
    val StatusGoodTint = Color(0xFFE7EFE4)
    val StatusBad = Color(0xFFAD5A44)
    val StatusBadTint = Color(0xFFF3E4DE)

    // 정보 없음 마커, 화장실 마커
    val MarkerNone = Neutral300
    val MarkerNoneIcon = Neutral700
    val MarkerRestroom = Accent
}
