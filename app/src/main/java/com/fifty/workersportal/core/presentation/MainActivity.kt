package com.fifty.workersportal.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import com.fifty.workersportal.core.presentation.component.Navigation
import com.fifty.workersportal.core.presentation.component.StandardScaffold
import com.fifty.workersportal.core.presentation.ui.theme.WorkersPortalTheme
import com.fifty.workersportal.featureauth.presentation.splash.SplashViewModel
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

class MainActivity : ComponentActivity() {

    private val splashScreenViewModel: SplashViewModel by viewModels()

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            this.setKeepOnScreenCondition {
                splashScreenViewModel.isLoading.value
            }
        }
        setContent {
            WorkersPortalTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberAnimatedNavController()
                    val snackBarHostState = remember { SnackbarHostState() }
                    StandardScaffold(
                        navController = navController,
                        snackBarHostState = snackBarHostState,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Navigation(navController)
                    }
                }
            }
        }
    }
}