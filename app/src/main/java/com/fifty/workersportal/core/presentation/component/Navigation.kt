package com.fifty.workersportal.core.presentation.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.fifty.workersportal.core.presentation.util.NavArgConstants.NAV_ARG_COUNTRY_CODE
import com.fifty.workersportal.core.presentation.util.NavArgConstants.NAV_ARG_PHONE_NUMBER
import com.fifty.workersportal.core.presentation.util.enterTransitionHorizontal
import com.fifty.workersportal.core.presentation.util.enterTransitionVertical
import com.fifty.workersportal.core.presentation.util.exitTransitionHorizontal
import com.fifty.workersportal.core.presentation.util.exitTransitionVertical
import com.fifty.workersportal.core.presentation.util.popEnterTransitionHorizontal
import com.fifty.workersportal.core.presentation.util.popEnterTransitionVertical
import com.fifty.workersportal.core.presentation.util.popExitTransitionHorizontal
import com.fifty.workersportal.core.presentation.util.popExitTransitionVertical
import com.fifty.workersportal.core.util.NavigationParent
import com.fifty.workersportal.core.util.Screen
import com.fifty.workersportal.featureauth.presentation.auth.AuthScreen
import com.fifty.workersportal.featureauth.presentation.otpverification.OtpVerificationScreen
import com.fifty.workersportal.featureauth.presentation.selectcountry.SelectCountryCodeScreen
import com.fifty.workersportal.featurechat.presentation.message.MessageScreen
import com.fifty.workersportal.featureprofile.presentation.workerprofile.WorkerProfileScreen
import com.fifty.workersportal.featureuser.presentation.testscreen.FavoriteScreen
import com.fifty.workersportal.featureuser.presentation.testscreen.HistoryScreen
import com.fifty.workersportal.featureworker.presentation.workerdashboard.WorkerDashboardScreen
import com.fifty.workersportal.featureuser.presentation.userdashboard.UserDashboardScreen
import com.fifty.workersportal.featureworker.presentation.registerasworker.RegisterAsWorkerScreen
import com.fifty.workersportal.featureworker.presentation.selectworkercategory.SelectWorkerCategoryScreen
import com.fifty.workersportal.featureworker.presentation.workerlist.WorkerListScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    startDestination: String,
    onDataLoaded: () -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        onDataLoaded()
    }
    AnimatedNavHost(
        navController = navController,
        startDestination = NavigationParent.Work.route,
        enterTransition = { enterTransitionHorizontal },
        exitTransition = { exitTransitionHorizontal },
        popEnterTransition = { popEnterTransitionHorizontal },
        popExitTransition = { popExitTransitionHorizontal },
    ) {
        // Authentication nav destination
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
            startDestination = Screen.RegisterAsWorkerScreen.route,
            route = NavigationParent.Work.route
        ) {
            composable(Screen.RegisterAsWorkerScreen.route) {
                RegisterAsWorkerScreen()
            }
            composable(Screen.WorkerDashboardScreen.route) {
                WorkerDashboardScreen()
            }
            composable(Screen.SelectWorkerCategoryScreen.route) {
                SelectWorkerCategoryScreen(
                    onNavigate = navController::navigate,
                    onNavigateUp = navController::navigateUp
                )
            }
            composable(Screen.WorkerListScreen.route) {
                WorkerListScreen(
                    onNavigate = navController::navigate,
                    onNavigateUp = navController::navigateUp
                )
            }
            composable(Screen.WorkerProfileScreen.route) {
                WorkerProfileScreen(
                    onNavigate = navController::navigate,
                    onNavigateUp = navController::navigateUp
                )
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
            startDestination = Screen.MessageScreen.route,
            route = NavigationParent.Message.route
        ) {
            composable(Screen.MessageScreen.route) {
                MessageScreen(
                    onNavigateUp = navController::navigateUp
                )
            }
        }
    }
}