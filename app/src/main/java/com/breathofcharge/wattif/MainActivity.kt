package com.breathofcharge.wattif

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.breathofcharge.wattif.ui.MainUiState
import com.breathofcharge.wattif.ui.MainViewModel
import com.breathofcharge.wattif.ui.theme.WattIfTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { WattIfApp() }
    }
}

@Composable
fun WattIfApp() {
    val context = LocalContext.current.applicationContext
    val factory = remember(context) { MainViewModel.factory(context) }
    val viewModel: MainViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    WattIfTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                AppContent(uiState = uiState)
            }
        }
    }
}

@Composable
private fun AppContent(uiState: MainUiState) {
    Text(
        text = stringResource(R.string.app_name),
        style = MaterialTheme.typography.headlineLarge,
    )

    when (uiState) {
        MainUiState.Loading -> CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        is MainUiState.Ready -> Text(
            text = uiState.activeSession?.let { "진행 중: ${it.name}" }
                ?: stringResource(R.string.no_active_camping),
            style = MaterialTheme.typography.bodyLarge,
        )
        is MainUiState.Error -> Text(
            text = uiState.message,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WattIfAppPreview() {
    WattIfTheme {
        AppContent(uiState = MainUiState.Ready(activeSession = null))
    }
}
