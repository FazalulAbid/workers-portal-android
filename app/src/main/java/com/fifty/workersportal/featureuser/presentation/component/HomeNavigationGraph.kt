package com.fifty.workersportal.featureuser.presentation.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.fifty.workersportal.core.util.NavigationParent
import com.fifty.workersportal.core.util.Screen
import com.fifty.workersportal.featureuser.presentation.userdashboard.UserDashboardScreen
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.homeNavGraph(
    navController: NavController,
    snackbarHostState: SnackbarHostState
) {
    navigation(
        startDestination = Screen.UserDashboardScreen.route,
        route = NavigationParent.Home.route
    ) {
        composable(Screen.UserDashboardScreen.route) {
            UserDashboardScreen(
                onNavigate = navController::navigate
            )
        }
    }
}