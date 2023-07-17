package com.fifty.fixitnow.featureuser.presentation.component

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
import com.fifty.fixitnow.featureuser.presentation.edituserprofile.UpdateUserProfileScreen
import com.fifty.fixitnow.featureuser.presentation.userdashboard.UserDashboardScreen
import com.fifty.fixitnow.featureuser.presentation.userprofile.UserProfileScreen
import com.fifty.fixitnow.featureworkproposal.presentation.workproposal.WorkProposalViewModel
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.userNavGraph(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    workProposalViewModel: WorkProposalViewModel,
    imageLoader: ImageLoader
) {
    navigation(
        startDestination = Screen.UserDashboardScreen.route,
        route = NavigationParent.Home.route
    ) {
        composable(Screen.UserDashboardScreen.route) {
            UserDashboardScreen(
                onNavigate = navController::navigate,
                workProposalViewModel = workProposalViewModel,
                imageLoader = imageLoader
            )
        }
        composable(
            Screen.UserProfileScreen.route + "?userId={userId}",
            arguments = listOf(
                navArgument(name = "userId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) {
            val isUserProfileUpdated = it.savedStateHandle.get<Boolean>("isUserProfileUpdated")
            UserProfileScreen(
                onNavigate = navController::navigate,
                onNavigateUp = navController::navigateUp,
                imageLoader = imageLoader,
                isUserUpdated = isUserProfileUpdated == true,
                onNavigateWithPopBackStack = { route ->
                    navController.navigate(route) {
                        popUpTo(0)
                    }
                }
            )
        }
        composable(Screen.UpdateUserProfileScreen.route) {
            UpdateUserProfileScreen(
                onNavigateUp = navController::navigateUp,
                previousBackStackEntry = navController.previousBackStackEntry
            )
        }
    }
}