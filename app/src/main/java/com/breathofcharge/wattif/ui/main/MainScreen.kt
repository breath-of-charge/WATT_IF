package com.breathofcharge.wattif.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.breathofcharge.wattif.ui.components.MainTab
import com.breathofcharge.wattif.ui.components.WattIfTopBar
import com.breathofcharge.wattif.ui.dialog.CampingSummaryDialog
import com.breathofcharge.wattif.ui.dialog.DemoAlert
import com.breathofcharge.wattif.ui.dialog.DemoAlertDialog
import com.breathofcharge.wattif.ui.dialog.DepartureChecklistDialog
import com.breathofcharge.wattif.ui.dialog.EndCampingDialog
import com.breathofcharge.wattif.ui.dialog.RecordDetailDialog
import com.breathofcharge.wattif.ui.history.HistoryScreen
import com.breathofcharge.wattif.ui.home.CampingHomeScreen
import com.breathofcharge.wattif.ui.model.CampingPreset
import com.breathofcharge.wattif.ui.model.CampingRecord
import com.breathofcharge.wattif.ui.plan.CampingPlanDialog
import com.breathofcharge.wattif.ui.theme.WattIfTheme

private enum class Overlay { PLAN, END_CONFIRM, SUMMARY, CHECKLIST }

@Composable
fun MainScreen(
    viewModelFactory: ViewModelProvider.Factory,
    modifier: Modifier = Modifier,
) {
    val viewModel: MainViewModel = viewModel(factory = viewModelFactory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        MainUiState.Loading ->
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        is MainUiState.Ready ->
            WattIfExperience(
                initiallyActive = state.activeSession != null,
                modifier = modifier,
            )
        is MainUiState.Error ->
            WattIfExperience(
                initiallyActive = false,
                initialAlert = DemoAlert.VEHICLE,
                modifier = modifier,
            )
    }
}

@Composable
private fun WattIfExperience(
    initiallyActive: Boolean,
    modifier: Modifier = Modifier,
    initialAlert: DemoAlert? = null,
) {
    val isCompact = LocalConfiguration.current.screenWidthDp < 480
    var selectedTab by rememberSaveable { mutableStateOf(MainTab.CAMPING) }
    var isNightMode by rememberSaveable { mutableStateOf(false) }
    var isCampingActive by rememberSaveable { mutableStateOf(initiallyActive) }
    var selectedPreset by rememberSaveable { mutableStateOf(CampingPreset.REST) }
    var overlay by rememberSaveable { mutableStateOf<Overlay?>(null) }
    var selectedRecord by rememberSaveable { mutableStateOf<CampingRecord?>(null) }
    var alert by rememberSaveable { mutableStateOf(initialAlert) }

    WattIfTheme(darkTheme = isNightMode) {
        Column(
            modifier =
                modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = if (isCompact) 24.dp else 48.dp, vertical = if (isCompact) 20.dp else 30.dp),
        ) {
            WattIfTopBar(
                selectedTab = selectedTab,
                isNightMode = isNightMode,
                onTabSelected = { selectedTab = it },
                onNightModeClick = { isNightMode = !isNightMode },
            )
            Box(Modifier.weight(1f).padding(top = if (isCompact) 20.dp else 28.dp)) {
                when (selectedTab) {
                    MainTab.CAMPING ->
                        CampingHomeScreen(
                            isActive = isCampingActive,
                            selectedPreset = selectedPreset,
                            onPresetSelected = { selectedPreset = it },
                            onPlanClick = { overlay = Overlay.PLAN },
                            onEndClick = { overlay = Overlay.END_CONFIRM },
                            onControlClick = { control ->
                                if (control == "조명") alert = DemoAlert.PRESET
                            },
                        )
                    MainTab.HISTORY ->
                        HistoryScreen(onRecordClick = { selectedRecord = it })
                }
            }
        }

        when (overlay) {
            Overlay.PLAN ->
                CampingPlanDialog(
                    isEditing = isCampingActive,
                    onDismiss = { overlay = null },
                    onStart = {
                        isCampingActive = true
                        overlay = null
                        selectedTab = MainTab.CAMPING
                    },
                )
            Overlay.END_CONFIRM ->
                EndCampingDialog(
                    onDismiss = { overlay = null },
                    onSave = { overlay = Overlay.SUMMARY },
                    onDiscard = { overlay = Overlay.SUMMARY },
                )
            Overlay.SUMMARY -> CampingSummaryDialog(onContinue = { overlay = Overlay.CHECKLIST })
            Overlay.CHECKLIST ->
                DepartureChecklistDialog(
                    onLater = {
                        isCampingActive = false
                        overlay = null
                        selectedTab = MainTab.HISTORY
                    },
                    onComplete = {
                        isCampingActive = false
                        overlay = null
                        selectedTab = MainTab.HISTORY
                    },
                )
            null -> Unit
        }

        selectedRecord?.let { record ->
            RecordDetailDialog(
                record = record,
                onDismiss = { selectedRecord = null },
                onChecklist = {
                    selectedRecord = null
                    overlay = Overlay.CHECKLIST
                },
            )
        }

        alert?.let { alertType ->
            DemoAlertDialog(
                type = alertType,
                onDismiss = { alert = null },
                onConfirm = {
                    alert = null
                    if (alertType == DemoAlert.BATTERY) overlay = Overlay.END_CONFIRM
                },
            )
        }
    }
}

@Preview(widthDp = 1280, heightDp = 720)
@Composable
private fun WattIfExperiencePreview() {
    WattIfExperience(initiallyActive = false)
}
