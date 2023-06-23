package com.fifty.workersportal.featureworker.presentation.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import coil.ImageLoader
import com.fifty.workersportal.core.domain.util.Session
import com.fifty.workersportal.core.util.NavigationParent
import com.fifty.workersportal.core.util.Screen
import com.fifty.workersportal.featureworker.presentation.postsamplework.PostSampleWorkEvent
import com.fifty.workersportal.featureworker.presentation.postsamplework.PostSampleWorkScreen
import com.fifty.workersportal.featureworker.presentation.registerasworker.RegisterAsWorkerScreen
import com.fifty.workersportal.featureworker.presentation.reviewandrating.ReviewAndRatingScreen
import com.fifty.workersportal.featureworker.presentation.searchworker.SearchWorkerScreen
import com.fifty.workersportal.featureworker.presentation.selectworkercategory.SelectWorkerCategoryScreen
import com.fifty.workersportal.featureworker.presentation.workerdashboard.WorkerDashboardScreen
import com.fifty.workersportal.featureworker.presentation.workerlist.WorkerListScreen
import com.fifty.workersportal.featureworker.presentation.workerprofile.WorkerProfileScreen
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.workerNavGraph(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    imageLoader: ImageLoader,
    isWorker: Boolean = false
) {
    navigation(
        startDestination = if (Session.userSession.value?.isWorker == true) {
            Screen.WorkerDashboardScreen.route
        } else Screen.RegisterAsWorkerScreen.route,
        route = NavigationParent.Work.route
    ) {
        composable(Screen.RegisterAsWorkerScreen.route) {
            RegisterAsWorkerScreen(
                onNavigateUp = navController::navigateUp,
                imageLoader = imageLoader
            )
        }
        composable(Screen.WorkerDashboardScreen.route) {
            WorkerDashboardScreen(
                imageLoader = imageLoader,
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate
            )
        }
        composable(Screen.SearchCategoryScreen.route) {
            SelectWorkerCategoryScreen(
                onNavigate = navController::navigate,
                onNavigateUp = navController::navigateUp,
                imageLoader = imageLoader
            )
        }
        composable(Screen.WorkerListScreen.route) {
            WorkerListScreen(
                onNavigate = navController::navigate,
                onNavigateUp = navController::navigateUp
            )
        }
        composable(
            route = Screen.WorkerProfileScreen.route + "?userId={userId}",
            arguments = listOf(
                navArgument(name = "userId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) {
            WorkerProfileScreen(
                onNavigate = navController::navigate,
                onNavigateUp = navController::navigateUp,
                imageLoader = imageLoader
            )
        }
        composable(Screen.ReviewAndRatingScreen.route) {
            ReviewAndRatingScreen(
                onNavigate = navController::navigate,
                onNavigateUp = navController::navigateUp
            )
        }
        composable(Screen.SearchWorkerScreen.route) {
            SearchWorkerScreen(
                onNavigate = navController::navigate,
                onNavigateUp = navController::navigateUp
            )
        }
        composable(Screen.PostSampleWorkScreen.route) {
            PostSampleWorkScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                imageLoader = imageLoader
            )
        }
    }
}