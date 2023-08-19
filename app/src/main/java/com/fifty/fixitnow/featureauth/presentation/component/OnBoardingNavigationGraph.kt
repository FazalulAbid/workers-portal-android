package com.fifty.fixitnow.featureauth.presentation.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import coil.ImageLoader
import com.fifty.fixitnow.core.util.NavigationParent
import com.fifty.fixitnow.core.util.Screen
import com.fifty.fixitnow.featureauth.presentation.onboarding.OnBoardingScreen
import com.google.accompanist.navigation.animation.composable


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.onboardingNavGraph(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    imageLoader: ImageLoader
) {
    navigation(
        startDestination = Screen.OnBoardingScreen.route,
        route = NavigationParent.OnBoarding.route
    ) {
        composable(
            Screen.OnBoardingScreen.route
        ) {
            OnBoardingScreen(
                onNavigate = navController::navigate,
                onNavigateWithPopBackStack = { route ->
                    navController.navigate(route) {
                        popUpTo(0)
                    }
                },
                imageLoader = imageLoader
            )
        }
    }
}