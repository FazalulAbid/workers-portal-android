package com.fifty.workersportal.featurelocation.presentation.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.fifty.workersportal.core.util.NavigationParent
import com.fifty.workersportal.core.util.Screen
import com.fifty.workersportal.featurechat.presentation.chat.ChatScreen
import com.fifty.workersportal.featurechat.presentation.message.MessageScreen
import com.fifty.workersportal.featurelocation.presentation.detectcurrentlocation.DetectCurrentLocationScreen
import com.fifty.workersportal.featurelocation.presentation.selectlocation.SelectLocationScreen
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.locationNavGraph(
    navController: NavController,
    snackbarHostState: SnackbarHostState
) {
    composable(Screen.SelectLocationScreen.route) {
        SelectLocationScreen(
            onNavigateUp = navController::navigateUp,
            onNavigate = navController::navigate
        )
    }
    composable(Screen.DetectCurrentLocationScreen.route) {
        DetectCurrentLocationScreen(
            onNavigateUp = navController::navigateUp,
            onNavigate = navController::navigate
        )
    }
}