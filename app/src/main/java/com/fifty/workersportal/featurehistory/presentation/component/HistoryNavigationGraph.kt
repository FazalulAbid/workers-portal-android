package com.fifty.workersportal.featurehistory.presentation.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.fifty.workersportal.core.util.NavigationParent
import com.fifty.workersportal.core.util.Screen
import com.fifty.workersportal.featureuser.presentation.testscreen.HistoryScreen
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.historyNavGraph(
    navController: NavController,
    snackbarHostState: SnackbarHostState
) {
    navigation(
        startDestination = Screen.HistoryScreen.route,
        route = NavigationParent.History.route
    ) {
        composable(Screen.HistoryScreen.route) {
            HistoryScreen()
        }
    }
}