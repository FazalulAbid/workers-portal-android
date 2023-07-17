package com.fifty.fixitnow.featurelocation.presentation.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.fifty.fixitnow.core.util.Screen
import com.fifty.fixitnow.featurelocation.presentation.detectcurrentlocation.DetectCurrentLocationScreen
import com.fifty.fixitnow.featurelocation.presentation.selectlocation.SelectLocationScreen
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.locationNavGraph(
    navController: NavController,
    snackbarHostState: SnackbarHostState
) {
    composable(Screen.SelectLocationScreen.route) {
        val isRefreshNeeded = it.savedStateHandle.get<Boolean>("isRefreshNeeded")
        SelectLocationScreen(
            onNavigateUp = navController::navigateUp,
            onNavigate = navController::navigate,
            isRefreshNeeded = isRefreshNeeded == true
        )
    }
    composable(Screen.DetectCurrentLocationScreen.route) {
        DetectCurrentLocationScreen(
            onNavigateUp = navController::navigateUp,
            previousBackStackEntry = navController.previousBackStackEntry
        )
    }
}