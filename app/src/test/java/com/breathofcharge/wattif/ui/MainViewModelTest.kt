package com.breathofcharge.wattif.ui

import com.breathofcharge.wattif.domain.model.ActiveCampingSession
import com.breathofcharge.wattif.domain.repository.CampingSessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class MainViewModelTest {
    @Test
    fun activeSessionIsExposedAsReadyState() =
        runTest {
            val expected =
                ActiveCampingSession(
                    id = 1,
                    name = "한강 노을 캠핑",
                    status = "ACTIVE",
                    startedAtEpochMillis = 1_000,
                    startBatteryPercent = 82,
                    destinationName = "서울 성수동",
                    reserveBatteryPercent = 30,
                    activePresetName = "취침 모드",
                )
            val viewModel = MainViewModel(FakeRepository(flowOf(expected)))

            val state = viewModel.uiState.filterIsInstance<MainUiState.Ready>().first()

            assertEquals(expected, state.activeSession)
        }

    private class FakeRepository(
        private val sessions: Flow<ActiveCampingSession?>,
    ) : CampingSessionRepository {
        override fun observeActiveSession(): Flow<ActiveCampingSession?> = sessions
    }
}
