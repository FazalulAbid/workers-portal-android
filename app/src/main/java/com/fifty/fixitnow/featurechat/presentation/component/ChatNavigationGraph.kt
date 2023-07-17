package com.fifty.fixitnow.featurechat.presentation.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.fifty.fixitnow.core.util.NavigationParent
import com.fifty.fixitnow.core.util.Screen
import com.fifty.fixitnow.featurechat.presentation.chat.ChatScreen
import com.fifty.fixitnow.featurechat.presentation.message.MessageScreen
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.chatNavGraph(
    navController: NavController,
    snackbarHostState: SnackbarHostState
) {
    navigation(
        startDestination = Screen.ChatScreen.route,
        route = NavigationParent.Chat.route
    ) {
        composable(Screen.ChatScreen.route) {
            ChatScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate
            )
        }
        composable(Screen.MessageScreen.route) {
            MessageScreen(
                onNavigateUp = navController::navigateUp
            )
        }
    }
}