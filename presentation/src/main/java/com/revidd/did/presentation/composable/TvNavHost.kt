package com.revidd.did.presentation.composable

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.revidd.did.presentation.viewmodel.SignInViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TvNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen {
                navController.navigate("login") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }

        composable("login") {
            val signInViewmodel = koinViewModel<SignInViewModel>()
            val uiState = signInViewmodel.uiState.collectAsStateWithLifecycle().value
            LoginSignupScreen(signInUiState = uiState, intent = signInViewmodel::handleIntent)
        }
    }
}
