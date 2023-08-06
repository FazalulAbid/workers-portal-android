package com.fifty.fixitnow.featureauth.presentation.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import coil.ImageLoader
import com.fifty.fixitnow.core.util.NavigationParent
import com.fifty.fixitnow.core.util.Screen
import com.fifty.fixitnow.featureauth.presentation.auth.AuthScreen
import com.fifty.fixitnow.featureauth.presentation.otpverification.OtpVerificationScreen
import com.fifty.fixitnow.featureauth.presentation.selectcountry.SelectCountryCodeScreen
import com.google.accompanist.navigation.animation.composable

const val NAV_ARG_PHONE_NUMBER = "phoneNumber"
const val NAV_ARG_COUNTRY_CODE = "countryCode"

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.authNavGraph(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    imageLoader: ImageLoader
) {
    navigation(
        startDestination = Screen.AuthScreen.route,
        route = NavigationParent.Auth.route
    ) {
        composable(
            Screen.AuthScreen.route
        ) {
            AuthScreen(
                onNavigateWithPopBackStack = { route ->
                    navController.navigate(route) {
                        popUpTo(0)
                    }
                },
                snackbarHostState = snackbarHostState,
                onNavigate = navController::navigate,
                currentBackStackEntry = navController.currentBackStackEntry,
                imageLoader = imageLoader
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
                        popUpTo(0)
                    }
                },
                onNavigateUp = navController::navigateUp,
            )
        }
        composable(Screen.SelectCountryScreen.route) {
            SelectCountryCodeScreen(
                previousBackStackEntry = navController.previousBackStackEntry,
                popBackStack = navController::popBackStack,
                onNavigateUp = navController::navigateUp,
                imageLoader = imageLoader
            )
        }
    }
}