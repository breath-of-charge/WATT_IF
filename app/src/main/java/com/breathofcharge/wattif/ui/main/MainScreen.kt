package com.breathofcharge.wattif.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.breathofcharge.wattif.R
import com.breathofcharge.wattif.ui.theme.WattIfTheme

@Composable
fun MainScreen(
    viewModelFactory: ViewModelProvider.Factory,
    modifier: Modifier = Modifier,
) {
    val viewModel: MainViewModel = viewModel(factory = viewModelFactory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MainContent(
        uiState = uiState,
        modifier = modifier,
    )
}

@Composable
private fun MainContent(
    uiState: MainUiState,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineLarge,
            )

            when (uiState) {
                MainUiState.Loading ->
                    CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
                is MainUiState.Ready ->
                    Text(
                        text =
                            uiState.activeSession?.let { "진행 중: ${it.name}" }
                                ?: stringResource(R.string.no_active_camping),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                is MainUiState.Error ->
                    Text(
                        text = uiState.message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge,
                    )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainContentPreview() {
    WattIfTheme {
        MainContent(uiState = MainUiState.Ready(activeSession = null))
    }
}
