package com.fifty.workersportal.core.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.fifty.workersportal.core.presentation.component.Navigation
import com.fifty.workersportal.core.presentation.component.StandardScaffold
import com.fifty.workersportal.core.presentation.ui.theme.WorkersPortalTheme
import com.fifty.workersportal.core.util.NavigationParent
import com.fifty.workersportal.featureauth.presentation.splash.SplashEvent
import com.fifty.workersportal.featureauth.presentation.splash.SplashViewModel
import com.fifty.workersportal.featureauth.presentation.splash.UserAuthState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val TAG = "Hello MainActivity"

    private val splashScreenViewModel: SplashViewModel by viewModels()
    private var userAuthState = mutableStateOf(UserAuthState.UNKNOWN)

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splashScreenViewModel.onEvent(SplashEvent.CheckAuthentication)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                splashScreenViewModel.isAuthenticated.collect {
                    userAuthState.value = it
                }
            }
        }

        setContent {
            WorkersPortalTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val splash = installSplashScreen()
                    splash.setKeepOnScreenCondition {
                        userAuthState.value != UserAuthState.UNKNOWN
                    }

                    val navController = rememberAnimatedNavController()
                    val snackBarHostState = remember { SnackbarHostState() }
                    StandardScaffold(
                        navController = navController,
                        snackBarHostState = snackBarHostState,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        when (userAuthState.value) {
                            UserAuthState.UNAUTHENTICATED -> {
                                Navigation(
                                    navController = navController,
                                    snackbarHostState = snackBarHostState,
                                    startDestination = NavigationParent.Auth.route
                                )
                            }

                            UserAuthState.AUTHENTICATED -> {
                                Navigation(
                                    navController = navController,
                                    snackbarHostState = snackBarHostState,
                                    startDestination = NavigationParent.Auth.route
                                )
                            }

                            UserAuthState.UNKNOWN -> {}
                        }
                    }
                }
            }
        }
    }
}