package com.fifty.fixitnow.featureworker.presentation.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import coil.ImageLoader
import com.fifty.fixitnow.core.domain.util.Session
import com.fifty.fixitnow.core.util.NavigationParent
import com.fifty.fixitnow.core.util.Screen
import com.fifty.fixitnow.featureworker.presentation.postsamplework.PostSampleWorkScreen
import com.fifty.fixitnow.featureworker.presentation.registerasworker.RegisterAsWorkerScreen
import com.fifty.fixitnow.featureworker.presentation.reviewandrating.ReviewAndRatingScreen
import com.fifty.fixitnow.featureworker.presentation.searchworker.SearchWorkerScreen
import com.fifty.fixitnow.featureworker.presentation.selectworkercategory.SelectWorkerCategoryScreen
import com.fifty.fixitnow.featureworker.presentation.workerdashboard.WorkerDashboardScreen
import com.fifty.fixitnow.featureworker.presentation.workerprofile.WorkerProfileScreen
import com.fifty.fixitnow.featureworkproposal.presentation.workproposal.WorkProposalScreen
import com.fifty.fixitnow.featureworkproposal.presentation.workproposal.WorkProposalViewModel
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.workerNavGraph(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    imageLoader: ImageLoader,
    workProposalViewModel: WorkProposalViewModel,
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
                imageLoader = imageLoader,
                previousBackStackEntry = navController.previousBackStackEntry
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
                imageLoader = imageLoader,
                workProposalViewModel = workProposalViewModel
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
        ) { entry ->
            val isSampleWorkAdded = entry.savedStateHandle.get<Boolean>("isSampleWorkAdded")
            val isWorkerProfileUpdated =
                entry.savedStateHandle.get<Boolean>("isWorkerProfileUpdated")
            WorkerProfileScreen(
                workerId = entry.arguments?.getString("userId"),
                onNavigate = navController::navigate,
                onNavigateUp = navController::navigateUp,
                isSampleWorkAdded = isSampleWorkAdded == true,
                isWorkerProfileUpdated = isWorkerProfileUpdated == true,
                imageLoader = imageLoader,
                workProposalViewModel = workProposalViewModel
            )
        }
        composable(Screen.ReviewAndRatingScreen.route + "?userId={userId}") {
            ReviewAndRatingScreen(
                onNavigateUp = navController::navigateUp,
                imageLoader = imageLoader
            )
        }
        composable(
            route = Screen.SearchWorkerScreen.route + "?categoryId={categoryId}",
            arguments = listOf(
                navArgument(name = "categoryId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) {
            SearchWorkerScreen(
                onNavigate = navController::navigate,
                onNavigateUp = navController::navigateUp,
                imageLoader = imageLoader
            )
        }
        composable(Screen.PostSampleWorkScreen.route) {
            PostSampleWorkScreen(
                onNavigateUp = navController::navigateUp,
                imageLoader = imageLoader,
                previousBackStackEntry = navController.previousBackStackEntry
            )
        }
        composable(Screen.WorkProposalScreen.route) {
            WorkProposalScreen(
                onNavigate = navController::navigate,
                popBackStackUpTo = navController::popBackStack,
                onNavigateUp = navController::navigateUp,
                imageLoader = imageLoader,
                viewModel = workProposalViewModel
            )
        }
    }
}