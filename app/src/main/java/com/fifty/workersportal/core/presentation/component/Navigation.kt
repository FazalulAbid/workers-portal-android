package com.fifty.workersportal.core.presentation.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.fifty.workersportal.core.util.NavigationParent
import com.fifty.workersportal.core.util.Screen
import com.fifty.workersportal.featureauth.presentation.auth.AuthScreen
import com.fifty.workersportal.featureauth.presentation.otp.OtpScreen
import com.fifty.workersportal.featureauth.presentation.selectcountry.SelectCountryCodeScreen
import com.fifty.workersportal.featureuserdashboard.presentation.testscreen.FavoriteScreen
import com.fifty.workersportal.featureuserdashboard.presentation.testscreen.HistoryScreen
import com.fifty.workersportal.featureuserdashboard.presentation.testscreen.WorkerDashboardScreen
import com.fifty.workersportal.featureuserdashboard.presentation.userdashboard.UserDashboardScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.AuthScreen.route
    ) {
        // Home Nav destination
        navigation(
            startDestination = Screen.UserDashboardScreen.route,
            route = NavigationParent.Home.label
        ) {
            composable(Screen.UserDashboardScreen.route) {
                UserDashboardScreen()
            }
        }
        // Work Nav destination
        navigation(
            startDestination = Screen.WorkerDashboardScreen.route,
            route = NavigationParent.Work.label
        ) {
            composable(Screen.WorkerDashboardScreen.route) {
                WorkerDashboardScreen()
            }
        }
        // Favorite Nav destination
        navigation(
            startDestination = Screen.FavoriteScreen.route,
            route = NavigationParent.Favorite.label
        ) {
            composable(Screen.FavoriteScreen.route) {
                FavoriteScreen()
            }
        }
        // Home Nav destination
        navigation(
            startDestination = Screen.HistoryScreen.route,
            route = NavigationParent.History.label
        ) {
            composable(Screen.HistoryScreen.route) {
                HistoryScreen()
            }
        }
        composable(Screen.AuthScreen.route) {
            AuthScreen(
                snackbarHostState = snackbarHostState,
                onNavigate = navController::navigate,
                currentBackStackEntry = navController.currentBackStackEntry
            )
        }
        composable(Screen.OtpScreen.route) {
            OtpScreen()
        }
        composable(Screen.SelectCountryScreen.route) {
            SelectCountryCodeScreen(navController = navController)
        }
    }
}