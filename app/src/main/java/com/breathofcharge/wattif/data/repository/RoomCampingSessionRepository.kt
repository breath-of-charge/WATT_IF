package com.breathofcharge.wattif.data.repository

import com.breathofcharge.wattif.data.local.CampingSessionDao
import com.breathofcharge.wattif.domain.model.ActiveCampingSession
import com.breathofcharge.wattif.domain.repository.CampingSessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomCampingSessionRepository(
    private val dao: CampingSessionDao,
) : CampingSessionRepository {
    override fun observeActiveSession(): Flow<ActiveCampingSession?> =
        dao.observeActiveSession().map { entity ->
            entity?.let {
                ActiveCampingSession(
                    id = it.id,
                    name = it.name ?: "이름 없는 캠핑",
                    status = it.status,
                    startedAtEpochMillis = it.startedAtEpochMillis,
                    startBatteryPercent = it.startBatteryPercent,
                    destinationName = it.destinationName,
                    reserveBatteryPercent = it.reserveBatteryPercent,
                    activePresetName = it.activePresetName,
                )
            }
        }
}
