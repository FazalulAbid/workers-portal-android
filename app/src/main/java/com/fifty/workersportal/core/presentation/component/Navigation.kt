package com.fifty.workersportal.core.presentation.component

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import com.fifty.workersportal.core.util.Screen
import com.fifty.workersportal.featureauth.presentation.auth.AuthScreen
import com.fifty.workersportal.featureauth.presentation.otp.OtpScreen
import com.fifty.workersportal.featureuserdashboard.presentation.userdashboard.UserDashboardScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(
    navController: NavHostController
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.UserDashboardScreen.route
    ) {
        // AuthScreen destination
        composable(Screen.AuthScreen.route) {
            AuthScreen()
        }
        // Otp screen destination
        composable(Screen.OtpScreen.route) {
            OtpScreen()
        }
        // User dashboard destination
        composable(Screen.UserDashboardScreen.route) {
            UserDashboardScreen()
        }
    }
}