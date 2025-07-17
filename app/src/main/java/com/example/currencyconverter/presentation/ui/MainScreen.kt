package com.example.currencyconverter.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.presentation.viewmodel.ConversionIntent
import com.example.currencyconverter.presentation.viewmodel.ConversionViewModel

@SuppressLint("DefaultLocale")
@Composable
fun MainScreen(viewModel: ConversionViewModel) {
    val state by viewModel.state.collectAsState()

    var baseCurrency by remember { mutableStateOf("USD") }
    var amountText by remember { mutableStateOf("1.0") }

    Column(Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = amountText,
            onValueChange = { amountText = it },
            label = { Text("Amount") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = baseCurrency,
            onValueChange = { baseCurrency = it },
            label = { Text("Base Currency (e.g. USD)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                amountText.toDoubleOrNull()?.let {
                    viewModel.handleIntent(ConversionIntent.Convert(baseCurrency, it))
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Convert")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            state.isLoading -> CircularProgressIndicator()
            state.error != null -> Text("Error: ${state.error}", color = Color.Red)
            else -> LazyColumn {
                items(state.conversions) { result ->
                    Text("${result.currencyCode}: ${String.format("%.2f", result.convertedAmount)}")
                }
            }
        }
    }
}
