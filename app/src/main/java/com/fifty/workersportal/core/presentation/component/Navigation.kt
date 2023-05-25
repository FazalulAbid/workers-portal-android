package com.fifty.workersportal.core.presentation.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.fifty.workersportal.core.presentation.util.NavArgConstants.NAV_ARG_COUNTRY_CODE
import com.fifty.workersportal.core.presentation.util.NavArgConstants.NAV_ARG_PHONE_NUMBER
import com.fifty.workersportal.core.util.NavigationParent
import com.fifty.workersportal.core.util.Screen
import com.fifty.workersportal.featureauth.presentation.auth.AuthScreen
import com.fifty.workersportal.featureauth.presentation.otp.OtpVerificationScreen
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
        startDestination = NavigationParent.Auth.route
    ) {
        // Home Nav destination
        navigation(
            startDestination = Screen.UserDashboardScreen.route,
            route = NavigationParent.Home.route
        ) {
            composable(Screen.UserDashboardScreen.route) {
                UserDashboardScreen()
            }
        }
        // Work Nav destination
        navigation(
            startDestination = Screen.WorkerDashboardScreen.route,
            route = NavigationParent.Work.route
        ) {
            composable(Screen.WorkerDashboardScreen.route) {
                WorkerDashboardScreen()
            }
        }
        // Favorite Nav destination
        navigation(
            startDestination = Screen.FavoriteScreen.route,
            route = NavigationParent.Favorite.route
        ) {
            composable(Screen.FavoriteScreen.route) {
                FavoriteScreen()
            }
        }
        // Home Nav destination
        navigation(
            startDestination = Screen.HistoryScreen.route,
            route = NavigationParent.History.route
        ) {
            composable(Screen.HistoryScreen.route) {
                HistoryScreen()
            }
        }
        navigation(
            startDestination = Screen.AuthScreen.route,
            route = NavigationParent.Auth.route
        ) {
            composable(Screen.AuthScreen.route) {
                AuthScreen(
                    snackbarHostState = snackbarHostState,
                    onNavigate = navController::navigate,
                    currentBackStackEntry = navController.currentBackStackEntry
                )
            }
            composable(
                Screen.OtpVerificationScreen.route + "/{${NAV_ARG_COUNTRY_CODE}}/{${NAV_ARG_PHONE_NUMBER}}",
                arguments = listOf(
                    navArgument(NAV_ARG_PHONE_NUMBER) {
                        type = NavType.StringType
                    },
                    navArgument(NAV_ARG_COUNTRY_CODE) {
                        type = NavType.StringType
                    }
                )
            ) {
                OtpVerificationScreen(
                    onNavigateWithPopBackStack = { route ->
                        navController.navigate(route) {
                            popUpTo(Screen.OtpVerificationScreen.route) {
                                inclusive = true
                            }
                        }
                    },
                    onNavigate = navController::navigate,
                    onNavigateUp = navController::navigateUp,
                )
            }
            composable(Screen.SelectCountryScreen.route) {
                SelectCountryCodeScreen(
                    previousBackStackEntry = navController.previousBackStackEntry,
                    popBackStack = navController::popBackStack,
                    onNavigateUp = navController::navigateUp
                )
            }
        }

    }
}