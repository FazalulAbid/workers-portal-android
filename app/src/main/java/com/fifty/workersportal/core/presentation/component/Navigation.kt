package com.fifty.workersportal.core.presentation.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.fifty.workersportal.core.util.Screen
import com.fifty.workersportal.featureauth.presentation.auth.AuthScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation() {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.AuthScreen.route
    ) {
        // AuthScreen destination
        composable(Screen.AuthScreen.route) {
            AuthScreen()
        }
    }
}