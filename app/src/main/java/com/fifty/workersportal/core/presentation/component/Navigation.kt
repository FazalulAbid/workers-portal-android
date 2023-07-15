package com.fifty.workersportal.core.presentation.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import coil.ImageLoader
import com.fifty.workersportal.core.presentation.util.enterTransitionHorizontal
import com.fifty.workersportal.core.presentation.util.exitTransitionHorizontal
import com.fifty.workersportal.core.presentation.util.popEnterTransitionHorizontal
import com.fifty.workersportal.core.presentation.util.popExitTransitionHorizontal
import com.fifty.workersportal.featureauth.presentation.component.authNavGraph
import com.fifty.workersportal.featurechat.presentation.component.chatNavGraph
import com.fifty.workersportal.featurefavorites.presentation.component.favouriteNavGraph
import com.fifty.workersportal.featurehistory.presentation.component.historyNavGraph
import com.fifty.workersportal.featurelocation.presentation.component.locationNavGraph
import com.fifty.workersportal.featureuser.presentation.component.userNavGraph
import com.fifty.workersportal.featureworker.presentation.component.workerNavGraph
import com.fifty.workersportal.featureworkproposal.presentation.workproposal.WorkProposalViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    startDestination: String,
    imageLoader: ImageLoader,
    isWorker: Boolean,
    workProposalViewModel: WorkProposalViewModel,
    onDataLoaded: () -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        onDataLoaded()
    }
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { enterTransitionHorizontal },
        exitTransition = { exitTransitionHorizontal },
        popEnterTransition = { popEnterTransitionHorizontal },
        popExitTransition = { popExitTransitionHorizontal },
    ) {
        authNavGraph(navController, snackbarHostState, imageLoader)
        userNavGraph(navController, snackbarHostState, workProposalViewModel, imageLoader)
        workerNavGraph(navController, snackbarHostState, imageLoader, workProposalViewModel)
        favouriteNavGraph(navController, imageLoader, snackbarHostState)
        historyNavGraph(navController, snackbarHostState)
        chatNavGraph(navController, snackbarHostState)
        locationNavGraph(navController, snackbarHostState)
    }
}