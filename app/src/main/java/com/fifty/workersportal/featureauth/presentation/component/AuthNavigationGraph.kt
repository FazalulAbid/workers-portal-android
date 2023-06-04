package com.fifty.workersportal.featureauth.presentation.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.fifty.workersportal.core.util.NavigationParent
import com.fifty.workersportal.core.util.Screen
import com.fifty.workersportal.featureauth.presentation.auth.AuthScreen
import com.fifty.workersportal.featureauth.presentation.otpverification.OtpVerificationScreen
import com.fifty.workersportal.featureauth.presentation.selectcountry.SelectCountryCodeScreen
import com.google.accompanist.navigation.animation.composable

const val NAV_ARG_PHONE_NUMBER = "phoneNumber"
const val NAV_ARG_COUNTRY_CODE = "countryCode"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.authNavGraph(
    navController: NavController,
    snackbarHostState: SnackbarHostState
) {
    navigation(
        startDestination = Screen.AuthScreen.route,
        route = NavigationParent.Auth.route
    ) {
        composable(
            Screen.AuthScreen.route
        ) {
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
                        popUpTo(NavigationParent.Auth.route) {
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