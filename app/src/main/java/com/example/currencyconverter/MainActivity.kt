package com.example.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.example.currencyconverter.presentation.ui.MainScreen
import com.example.currencyconverter.presentation.viewmodel.ConversionViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: ConversionViewModel = koinViewModel()
            MaterialTheme {
                MainScreen(viewModel)
            }
        }
    }
}