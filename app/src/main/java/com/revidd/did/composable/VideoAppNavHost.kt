package com.revidd.did.composable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.koin.androidx.compose.koinViewModel
import states.HomeIntent
import viewmodel.VideoViewModel

@Composable
fun VideoAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "home"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("home") {
            val viewModel: VideoViewModel =  koinViewModel<VideoViewModel>()
            val uiState by viewModel.uiState.collectAsState()
            HomeScreen(
                uiState = uiState,
                intent = { intent ->
                    when(intent){
                        is HomeIntent.OpenVideo -> navController.navigate("player?url=${intent.video.videoUrl}")
                        else -> viewModel.handleIntent(intent)
                    }},
            )
        }
        composable(route = "player?url={url}",
            arguments = listOf(
                navArgument("url") { type = NavType.StringType }
            ) ){ backStackEntry ->
            val videoUrl = backStackEntry.arguments?.getString("url") ?: ""
            VideoPlayerScreen(
                videoUrl = videoUrl,
                onBack = { navController.popBackStack() }
            )
        }
    }
}