package com.fifty.workersportal.featureworker.presentation.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.fifty.workersportal.core.util.NavigationParent
import com.fifty.workersportal.core.util.Screen
import com.fifty.workersportal.featureprofile.presentation.workerprofile.WorkerProfileScreen
import com.fifty.workersportal.featureworker.presentation.registerasworker.RegisterAsWorkerScreen
import com.fifty.workersportal.featureworker.presentation.selectworkercategory.SelectWorkerCategoryScreen
import com.fifty.workersportal.featureworker.presentation.workerdashboard.WorkerDashboardScreen
import com.fifty.workersportal.featureworker.presentation.workerlist.WorkerListScreen
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.workerNavGraph(
    navController: NavController,
    snackbarHostState: SnackbarHostState
) {
    navigation(
        startDestination = Screen.WorkerDashboardScreen.route,
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
}