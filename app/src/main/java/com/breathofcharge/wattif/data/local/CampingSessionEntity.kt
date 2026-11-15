package com.breathofcharge.wattif.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "camping_sessions")
data class CampingSessionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String?,
    val status: String,
    val startedAtEpochMillis: Long,
    val startBatteryPercent: Int?,
    val destinationName: String?,
    val reserveBatteryPercent: Int?,
    val activePresetName: String?,
    val endedAtEpochMillis: Long? = null,
    val endBatteryPercent: Int? = null,
) {
    companion object {
        const val STATUS_ACTIVE = "ACTIVE"
        const val STATUS_ENDING = "ENDING"
        const val STATUS_RESTORE_PARTIAL = "RESTORE_PARTIAL"
        const val STATUS_COMPLETED = "COMPLETED"
    }
}
