package com.breathofcharge.wattif.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.breathofcharge.wattif.data.local.entity.CampingSessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CampingSessionDao {
    @Query(
        """
        SELECT * FROM camping_sessions
        WHERE status IN ('ACTIVE', 'ENDING', 'RESTORE_PARTIAL')
        ORDER BY startedAtEpochMillis DESC
        LIMIT 1
        """,
    )
    fun observeActiveSession(): Flow<CampingSessionEntity?>

    @Upsert
    suspend fun upsert(session: CampingSessionEntity): Long

    @Query(
        """
        UPDATE camping_sessions
        SET status = :status,
            endedAtEpochMillis = :endedAtEpochMillis,
            endBatteryPercent = :endBatteryPercent
        WHERE id = :sessionId
        """,
    )
    suspend fun updateCompletion(
        sessionId: Long,
        status: String,
        endedAtEpochMillis: Long?,
        endBatteryPercent: Int?,
    )
}
