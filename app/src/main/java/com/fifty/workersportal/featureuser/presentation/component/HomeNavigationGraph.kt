package com.fifty.workersportal.featureuser.presentation.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import coil.ImageLoader
import com.fifty.workersportal.core.util.NavigationParent
import com.fifty.workersportal.core.util.Screen
import com.fifty.workersportal.featureuser.presentation.edituserprofile.EditUserProfileScreen
import com.fifty.workersportal.featureuser.presentation.userdashboard.UserDashboardScreen
import com.fifty.workersportal.featureuser.presentation.userprofile.UserProfileScreen
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.userNavGraph(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    imageLoader: ImageLoader
) {
    navigation(
        startDestination = Screen.UserDashboardScreen.route,
        route = NavigationParent.Home.route
    ) {
        composable(Screen.UserDashboardScreen.route) {
            UserDashboardScreen(
                onNavigate = navController::navigate,
                imageLoader
            )
        }
        composable(Screen.UserProfileScreen.route) {
            UserProfileScreen(
                onNavigate = navController::navigate,
                onNavigateUp = navController::navigateUp,
                onNavigateWithPopBackStack = { route ->
                    navController.navigate(route) {
                        popUpTo(0)
                    }
                }
            )
        }
        composable(Screen.EditUserProfileScreen.route) {
            EditUserProfileScreen(
                onNavigate = navController::navigate,
                onNavigateUp = navController::navigateUp,
            )
        }
    }
}