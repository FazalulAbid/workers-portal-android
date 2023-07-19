package com.fifty.fixitnow.featurehistory.presentation.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.fifty.fixitnow.core.util.NavigationParent
import com.fifty.fixitnow.core.util.Screen
import com.fifty.fixitnow.featurehistory.presentation.historyscreen.WorkHistoryScreen
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
            WorkHistoryScreen()
        }
    }
}