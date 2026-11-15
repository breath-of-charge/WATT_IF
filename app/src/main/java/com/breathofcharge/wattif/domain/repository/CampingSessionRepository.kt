package com.breathofcharge.wattif.domain.repository

import com.breathofcharge.wattif.domain.model.ActiveCampingSession
import kotlinx.coroutines.flow.Flow

interface CampingSessionRepository {
    fun observeActiveSession(): Flow<ActiveCampingSession?>
}
