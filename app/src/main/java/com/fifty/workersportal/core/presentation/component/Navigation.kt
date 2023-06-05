package com.fifty.workersportal.core.presentation.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.fifty.workersportal.core.presentation.util.enterTransitionHorizontal
import com.fifty.workersportal.core.presentation.util.exitTransitionHorizontal
import com.fifty.workersportal.core.presentation.util.popEnterTransitionHorizontal
import com.fifty.workersportal.core.presentation.util.popExitTransitionHorizontal
import com.fifty.workersportal.core.util.NavigationParent
import com.fifty.workersportal.core.util.Screen
import com.fifty.workersportal.featureauth.presentation.component.authNavGraph
import com.fifty.workersportal.featurechat.presentation.component.chatNavGraph
import com.fifty.workersportal.featurechat.presentation.message.MessageScreen
import com.fifty.workersportal.featurefavorites.presentation.component.favouriteNavGraph
import com.fifty.workersportal.featurehistory.presentation.component.historyNavGraph
import com.fifty.workersportal.featureprofile.presentation.workerprofile.WorkerProfileScreen
import com.fifty.workersportal.featureuser.presentation.component.homeNavGraph
import com.fifty.workersportal.featureuser.presentation.testscreen.FavoriteScreen
import com.fifty.workersportal.featureuser.presentation.testscreen.HistoryScreen
import com.fifty.workersportal.featureworker.presentation.component.workerNavGraph
import com.fifty.workersportal.featureworker.presentation.registerasworker.RegisterAsWorkerScreen
import com.fifty.workersportal.featureworker.presentation.selectworkercategory.SelectWorkerCategoryScreen
import com.fifty.workersportal.featureworker.presentation.workerdashboard.WorkerDashboardScreen
import com.fifty.workersportal.featureworker.presentation.workerlist.WorkerListScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

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
        authNavGraph(navController, snackbarHostState)
        homeNavGraph(navController, snackbarHostState)
        workerNavGraph(navController, snackbarHostState)
        favouriteNavGraph(navController, snackbarHostState)
        historyNavGraph(navController, snackbarHostState)
        chatNavGraph(navController, snackbarHostState)
    }
}