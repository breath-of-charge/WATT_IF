package com.breathofcharge.wattif.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.breathofcharge.wattif.domain.repository.CampingSessionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    repository: CampingSessionRepository,
) : ViewModel() {
    val uiState =
        repository
            .observeActiveSession()
            .map { session -> MainUiState.Ready(activeSession = session) as MainUiState }
            .catch { error ->
                emit(MainUiState.Error(error.message ?: "캠핑 상태를 불러오지 못했습니다."))
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
                initialValue = MainUiState.Loading,
            )

    companion object {
        fun factory(repository: CampingSessionRepository): ViewModelProvider.Factory =
            viewModelFactory {
                initializer { MainViewModel(repository) }
            }
    }
}
