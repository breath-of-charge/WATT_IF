package com.breathofcharge.wattif.ui

import com.breathofcharge.wattif.domain.model.ActiveCampingSession

sealed interface MainUiState {
    data object Loading : MainUiState

    data class Ready(
        val activeSession: ActiveCampingSession?,
    ) : MainUiState

    data class Error(
        val message: String,
    ) : MainUiState
}
