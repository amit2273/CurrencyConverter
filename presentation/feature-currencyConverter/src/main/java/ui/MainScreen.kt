package ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.domain.model.ConversionResult
import viewmodel.ConversionIntent
import viewmodel.ConversionState
import java.util.Locale
import AMOUNT
import BASE_CURRENCY
import CONVERT
import USD

private const val GRID_SIZE = 3

@Composable
fun MainScreen(state : ConversionState,
               handleIntent :(ConversionIntent) ->Unit) {
    var baseCurrency by remember { mutableStateOf(USD) }


    Column(Modifier.padding(horizontal = 16.dp, vertical = 20.dp)) {
        OutlinedTextField(
            value = state.enteredAmount,
            onValueChange = {
                handleIntent.invoke(ConversionIntent.ManipulateAmount(it))
                            },

            label = { Text(AMOUNT) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        CurrencyDropDown(
            baseCurrency = baseCurrency,
            availableCurrencies = state.availableCurrencies
        ) { baseCurrency = it }
        Spacer(modifier = Modifier.height(8.dp))
        ConvertButton(amountText = state.enteredAmount, baseCurrency = baseCurrency){
            handleIntent.invoke(it)
        }
        Spacer(modifier = Modifier.height(16.dp))
        when {
            state.isLoading -> CircularProgressIndicator()
            state.error != null -> Text("Error: ${state.error}", color = Color.Red)
            else -> CurrencyGrid(conversions = state.conversions)
        }
    }
}


@Composable
private fun CurrencyGrid(conversions : List<ConversionResult>){
    LazyVerticalGrid(
        columns = GridCells.Fixed(GRID_SIZE),
        modifier = Modifier.fillMaxHeight(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(conversions) { result ->
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
                        text = "${result.currencyCode}\n${String.format(Locale.US,"%.2f", result.convertedAmount)}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyDropDown(
    baseCurrency: String,
    availableCurrencies: Map<String, String>,
    baseCurrencyChange: (String) -> Unit
){
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = baseCurrency,
            onValueChange = {},
            readOnly = true,
            label = { Text(BASE_CURRENCY) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            availableCurrencies
                .toList()
                .sortedBy { it.first }
                .forEach { (code, name) ->
                    DropdownMenuItem(
                        text = { Text("$code - $name") },
                        onClick = {
                            baseCurrencyChange.invoke(code)
                            expanded = false
                        }
                    )
                }
        }
    }
}

@Composable
private fun ConvertButton(
    amountText: String, baseCurrency: String,
    handleIntent: (ConversionIntent) -> Unit
) {
    Button(
        onClick = {
            amountText.toDoubleOrNull()?.let {
                handleIntent(ConversionIntent.Convert(baseCurrency, it))
            }
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(CONVERT)
    }
}
