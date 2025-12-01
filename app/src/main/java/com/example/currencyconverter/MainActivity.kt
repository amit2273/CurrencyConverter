package com.example.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.DefaultLifecycleObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import ui.MainScreen
import viewmodel.ConversionViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext

class MainActivity : ComponentActivity() {
    private val conversionViewModel by viewModel<ConversionViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val coroutine = CoroutineScope(Dispatchers.Main).launch {
            supervisorScope {

            }

        }

        setContent {
            val state by conversionViewModel.state.collectAsState()
            MaterialTheme {
                MainScreen(
                    state = state,
                    handleIntent = conversionViewModel::handleIntent
                )
            }
        }
    }
}