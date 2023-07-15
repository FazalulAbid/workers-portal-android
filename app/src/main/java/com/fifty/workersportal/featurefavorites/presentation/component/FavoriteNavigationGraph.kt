package com.fifty.workersportal.featurefavorites.presentation.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import coil.ImageLoader
import com.fifty.workersportal.core.util.NavigationParent
import com.fifty.workersportal.core.util.Screen
import com.fifty.workersportal.featurefavorites.presentation.favouriteworkers.FavoriteWorkersScreen
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.favouriteNavGraph(
    navController: NavController,
    imageLoader: ImageLoader,
    snackbarHostState: SnackbarHostState
) {
    navigation(
        startDestination = Screen.FavoriteScreen.route,
        route = NavigationParent.Favorite.route
    ) {
        composable(Screen.FavoriteScreen.route) {
            FavoriteWorkersScreen(
                imageLoader = imageLoader,
                onNavigate = navController::navigate
            )
        }
    }
}