package com.revidd.did

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.revidd.did.presentation.composable.TvNavHost
import com.revidd.did.theme.ReviddTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReviddTheme {
                TvNavHost()
            }
        }
    }
}