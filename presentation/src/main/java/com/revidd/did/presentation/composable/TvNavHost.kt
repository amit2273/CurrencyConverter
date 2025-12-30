package com.revidd.did.presentation.composable

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.revidd.did.presentation.state.SignInEffect
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
            val lifecycleOwner = LocalLifecycleOwner.current
            val context = LocalContext.current
            LaunchedEffect(Unit) {
                lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    signInViewmodel.signInEffect.collect { effect ->
                        when (effect) {
                            SignInEffect.NavigateToHome -> {
                                navController.navigate("home")
                            }
                            SignInEffect.ShowToast ->{
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
            LoginSignupScreen(signInUiState = uiState, intent = signInViewmodel::handleIntent)
        }


        composable("home") {
            TvHomeScreen()
        }
    }
}
