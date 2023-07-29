package com.fifty.fixitnow.core.presentation.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import coil.ImageLoader
import com.fifty.fixitnow.core.presentation.util.enterTransitionHorizontal
import com.fifty.fixitnow.core.presentation.util.exitTransitionHorizontal
import com.fifty.fixitnow.core.presentation.util.popEnterTransitionHorizontal
import com.fifty.fixitnow.core.presentation.util.popExitTransitionHorizontal
import com.fifty.fixitnow.core.util.NavigationParent
import com.fifty.fixitnow.featureauth.presentation.component.authNavGraph
import com.fifty.fixitnow.featureauth.presentation.component.onboardingNavGraph
import com.fifty.fixitnow.featurechat.presentation.component.chatNavGraph
import com.fifty.fixitnow.featurefavorites.presentation.component.favouriteNavGraph
import com.fifty.fixitnow.featurehistory.presentation.component.historyNavGraph
import com.fifty.fixitnow.featurelocation.presentation.component.locationNavGraph
import com.fifty.fixitnow.featureuser.presentation.component.userNavGraph
import com.fifty.fixitnow.featureworker.presentation.component.workerNavGraph
import com.fifty.fixitnow.featureworkproposal.presentation.workproposal.WorkProposalViewModel
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
        onboardingNavGraph(navController, snackbarHostState, imageLoader)
        authNavGraph(navController, snackbarHostState, imageLoader)
        userNavGraph(navController, snackbarHostState, workProposalViewModel, imageLoader)
        workerNavGraph(navController, snackbarHostState, imageLoader, workProposalViewModel)
        favouriteNavGraph(navController, imageLoader, snackbarHostState)
        historyNavGraph(navController, snackbarHostState, imageLoader)
        chatNavGraph(navController, snackbarHostState)
        locationNavGraph(navController, snackbarHostState)
    }
}