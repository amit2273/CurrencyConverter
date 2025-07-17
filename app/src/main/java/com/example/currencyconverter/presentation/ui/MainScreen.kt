package com.example.currencyconverter.presentation.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.presentation.viewmodel.ConversionIntent
import com.example.currencyconverter.presentation.viewmodel.ConversionViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(viewModel: ConversionViewModel) {
    val state by viewModel.state.collectAsState()

    var baseCurrency by remember { mutableStateOf("USD") }
    var amountText by remember { mutableStateOf("1.0") }
    var expanded by remember { mutableStateOf(false) }

    Column(Modifier.padding(horizontal = 16.dp, vertical = 20.dp)) {
        OutlinedTextField(
            value = amountText,
            onValueChange = { amountText = it },
            label = { Text("Amount") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Currency Dropdown
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = baseCurrency,
                onValueChange = {},
                readOnly = true,
                label = { Text("Base Currency") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                state.availableCurrencies
                    .toList()
                    .sortedBy { it.first }
                    .forEach { (code, name) ->
                        DropdownMenuItem(
                            text = { Text("$code - $name") },
                            onClick = {
                                baseCurrency = code
                                expanded = false
                            }
                        )
                    }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Convert Button
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

        // Results in Grid
        when {
            state.isLoading -> CircularProgressIndicator()
            state.error != null -> Text("Error: ${state.error}", color = Color.Red)
            else -> LazyVerticalGrid(
                columns = GridCells.Fixed(3), // 3 columns as in mock
                modifier = Modifier.fillMaxHeight(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.conversions) { result ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        elevation = CardDefaults.cardElevation()
                    ) {
                        Box(
                            contentAlignment = androidx.compose.ui.Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "${result.currencyCode}\n${String.format("%.2f", result.convertedAmount)}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}
