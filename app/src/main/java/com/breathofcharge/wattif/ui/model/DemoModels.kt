package com.breathofcharge.wattif.ui.model

data class VehicleSummary(
    val batteryPercent: Int = 82,
    val drivingRangeKm: Int = 304,
    val indoorTemperature: Int = 24,
)

data class WeatherSummary(
    val location: String = "서울 성수동",
    val condition: String = "맑음",
    val temperature: Int = 8,
    val minimumTemperature: Int = 3,
    val rainChance: Int = 20,
    val windSpeed: Int = 2,
)

enum class CampingPreset(
    val title: String,
    val description: String,
) {
    REST("휴식 모드", "적당한 공조 · 은은한 조명"),
    MEDIA("미디어 모드", "화면 중심 · 조명 낮춤"),
    SLEEP("취침 모드", "조명 · 화면 밝기 최소화"),
}

data class CampingRecord(
    val date: String,
    val name: String,
    val duration: String,
    val batteryChange: String,
    val summary: String,
)

val sampleRecords =
    listOf(
        CampingRecord("11.12", "한강 노을 캠핑", "12h 34m", "18%", "휴식 모드 중심 · 설정 변경 3회"),
        CampingRecord("11.03", "가평 숲속 캠핑", "09h 10m", "14%", "휴식 모드 중심 · 설정 변경 3회"),
        CampingRecord("10.21", "을왕리 차박", "11h 42m", "21%", "미디어 모드 중심 · 설정 변경 2회"),
        CampingRecord("10.09", "남양주 새벽 캠핑", "08h 55m", "12%", "취침 모드 중심 · 설정 변경 1회"),
    )
