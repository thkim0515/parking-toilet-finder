pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // 네이버 지도 Android SDK 저장소 (SDK 연동 시 필요, 현재는 딥링크만 사용하므로 미사용)
        maven { url = uri("https://repository.map.naver.com/archive/maven") }
    }
}

rootProject.name = "parking-toilet-finder"
include(":app")
