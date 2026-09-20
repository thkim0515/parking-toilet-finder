package com.parkingtoiletfinder.app.ui.common

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.parkingtoiletfinder.app.data.model.Place

private const val NAVER_MAP_PACKAGE = "com.nhn.android.nmap"
private const val NAVER_MAP_PLAY_STORE_URL = "market://details?id=$NAVER_MAP_PACKAGE"
private const val NAVER_MAP_PLAY_STORE_WEB_URL =
    "https://play.google.com/store/apps/details?id=$NAVER_MAP_PACKAGE"

/**
 * 목적지까지 네이버 지도 앱의 길찾기 화면을 딥링크(nmap://route/...)로 실행한다.
 * 이 앱은 자체 내비게이션 기능을 구현하지 않고, 실제 길 안내는 네이버 지도 앱 안에서 이뤄진다.
 * 네이버 지도 앱이 설치돼 있지 않으면 플레이스토어 설치 페이지로 이동시킨다(fallback).
 *
 * 참고: nmap:// 딥링크는 호출하는 앱의 패키지명(appname)을 요구한다.
 * https://navermaps.github.io/android-map-sdk/guide-ko/9.html
 */
fun launchNaverMapDirections(context: Context, destination: Place) {
    val callerPackage = context.packageName
    val uri = Uri.parse(
        "nmap://route/walk?dlat=${destination.latitude}&dlng=${destination.longitude}" +
            "&dname=${Uri.encode(destination.name)}&appname=$callerPackage"
    )
    val intent = Intent(Intent.ACTION_VIEW, uri).apply {
        setPackage(NAVER_MAP_PACKAGE)
    }

    try {
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        openNaverMapInstallPage(context)
    }
}

private fun openNaverMapInstallPage(context: Context) {
    val marketIntent = Intent(Intent.ACTION_VIEW, Uri.parse(NAVER_MAP_PLAY_STORE_URL))
    try {
        context.startActivity(marketIntent)
    } catch (e: ActivityNotFoundException) {
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(NAVER_MAP_PLAY_STORE_WEB_URL))
        context.startActivity(webIntent)
    }
}
