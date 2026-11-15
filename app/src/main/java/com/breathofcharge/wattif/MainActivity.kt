package com.breathofcharge.wattif

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.breathofcharge.wattif.data.local.WattIfDatabase
import com.breathofcharge.wattif.data.repository.RoomCampingSessionRepository
import com.breathofcharge.wattif.ui.main.MainScreen
import com.breathofcharge.wattif.ui.main.MainViewModel
import com.breathofcharge.wattif.ui.theme.WattIfTheme

class MainActivity : ComponentActivity() {
    private val repository by lazy {
        RoomCampingSessionRepository(
            WattIfDatabase.getInstance(applicationContext).campingSessionDao(),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WattIfTheme {
                MainScreen(viewModelFactory = MainViewModel.factory(repository))
            }
        }
    }
}
