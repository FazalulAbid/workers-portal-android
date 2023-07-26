package com.fifty.fixitnow.core.presentation

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.ImageLoader
import com.fifty.fixitnow.core.domain.util.Session
import com.fifty.fixitnow.core.presentation.component.Navigation
import com.fifty.fixitnow.core.presentation.component.StandardScaffold
import com.fifty.fixitnow.core.presentation.ui.theme.WorkersPortalTheme
import com.fifty.fixitnow.core.util.NavigationParent
import com.fifty.fixitnow.core.util.Screen
import com.fifty.fixitnow.featureauth.presentation.splash.SplashEvent
import com.fifty.fixitnow.featureauth.presentation.splash.SplashViewModel
import com.fifty.fixitnow.featureauth.presentation.splash.UserAuthState
import com.fifty.fixitnow.featureworkproposal.presentation.workproposal.WorkProposalViewModel
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var imageLoader: ImageLoader

    private var keepSplashScreenOn = true
    private val splashScreenViewModel: SplashViewModel by viewModels()
    private var userAuthState = mutableStateOf(UserAuthState.UNKNOWN)

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splash = installSplashScreen()
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
                    val workProposalViewModel = hiltViewModel<WorkProposalViewModel>()
                    val navController = rememberAnimatedNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val snackBarHostState = remember { SnackbarHostState() }
//                    val splash = installSplashScreen()
                    splash.setKeepOnScreenCondition {
                        keepSplashScreenOn
                    }
                    StandardScaffold(
                        onNavigate = navController::navigate,
                        navBackStackEntry = navBackStackEntry,
                        snackBarHostState = snackBarHostState,
                        modifier = Modifier.fillMaxSize(),
                        showBottomBar = shouldShowBottomBar(navBackStackEntry),
                    ) {
                        when (userAuthState.value) {
                            UserAuthState.UNAUTHENTICATED -> {
                                Navigation(
                                    navController = navController,
                                    snackbarHostState = snackBarHostState,
                                    startDestination = NavigationParent.Auth.route,
                                    onDataLoaded = { keepSplashScreenOn = false },
                                    imageLoader = imageLoader,
                                    workProposalViewModel = workProposalViewModel,
                                    isWorker = Session.userSession.value?.isWorker ?: false
                                )
                            }

                            UserAuthState.AUTHENTICATED -> {
                                Navigation(
                                    navController = navController,
                                    snackbarHostState = snackBarHostState,
                                    startDestination = NavigationParent.Home.route,
                                    onDataLoaded = { keepSplashScreenOn = false },
                                    imageLoader = imageLoader,
                                    workProposalViewModel = workProposalViewModel,
                                    isWorker = Session.userSession.value?.isWorker ?: false
                                )
                            }

                            UserAuthState.UNKNOWN -> {}
                        }
                    }
                }
            }
        }
    }

    private fun shouldShowBottomBar(backStackEntry: NavBackStackEntry?): Boolean =
        backStackEntry?.destination?.route in listOf(
            Screen.UserDashboardScreen.route,
            Screen.WorkerDashboardScreen.route,
            Screen.FavoriteScreen.route,
            Screen.ChatScreen.route,
            Screen.HistoryScreen.route
        )
}