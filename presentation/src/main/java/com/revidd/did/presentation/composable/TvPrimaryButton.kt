package com.revidd.did.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Button
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.ButtonShape
import androidx.tv.material3.Text

@Composable
fun TvPrimaryButton(text: String, onClick : ()-> Unit) {
    Button(
        onClick = {onClick.invoke()},
        shape = ButtonDefaults.shape(shape = RectangleShape),
        modifier = Modifier
            .background(Color(0xFF00BFFF))
            .fillMaxWidth()
    ) {
        Text(text = text,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
             color = Color.White)
    }
}
