package com.breathofcharge.wattif.domain.model

data class ActiveCampingSession(
    val id: Long,
    val name: String,
    val status: String,
    val startedAtEpochMillis: Long,
    val startBatteryPercent: Int?,
    val destinationName: String?,
    val reserveBatteryPercent: Int?,
    val activePresetName: String?,
)
