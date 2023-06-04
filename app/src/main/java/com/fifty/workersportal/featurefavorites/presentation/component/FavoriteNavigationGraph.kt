package com.fifty.workersportal.featurefavorites.presentation.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.fifty.workersportal.core.util.NavigationParent
import com.fifty.workersportal.core.util.Screen
import com.fifty.workersportal.featureuser.presentation.testscreen.FavoriteScreen
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.favouriteNavGraph(
    navController: NavController,
    snackbarHostState: SnackbarHostState
) {
    navigation(
        startDestination = Screen.FavoriteScreen.route,
        route = NavigationParent.Favorite.route
    ) {
        composable(Screen.FavoriteScreen.route) {
            FavoriteScreen()
        }
    }
}