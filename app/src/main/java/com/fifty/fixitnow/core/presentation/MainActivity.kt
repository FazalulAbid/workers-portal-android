package com.fifty.fixitnow.core.presentation

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.ImageLoader
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.domain.util.Session
import com.fifty.fixitnow.core.presentation.component.Navigation
import com.fifty.fixitnow.core.presentation.component.StandardScaffold
import com.fifty.fixitnow.core.presentation.ui.theme.WorkersPortalTheme
import com.fifty.fixitnow.core.util.NavigationParent
import com.fifty.fixitnow.core.util.Screen
import com.fifty.fixitnow.featureauth.presentation.onboarding.OnBoardingEvent
import com.fifty.fixitnow.featureauth.presentation.splash.SplashEvent
import com.fifty.fixitnow.featureauth.presentation.splash.SplashViewModel
import com.fifty.fixitnow.featureauth.presentation.splash.UserAuthState
import com.fifty.fixitnow.featurechat.domain.usecase.ChatSocketUseCases
import com.fifty.fixitnow.featureworkproposal.presentation.workproposal.WorkProposalViewModel
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val LOCATION_PERMISSION_REQUEST_CODE = 1001

    @Inject
    lateinit var chatSocketUseCases: ChatSocketUseCases

    @Inject
    lateinit var imageLoader: ImageLoader

    private val splashViewModel: SplashViewModel by viewModels()
    private var startDestination = mutableStateOf<String?>(null)

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splash = installSplashScreen()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                splashViewModel.startDestination.collect { route ->
                    startDestination.value = route
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
                    splash.setKeepOnScreenCondition {
                        splashViewModel.isLoading.value
                    }
                    StandardScaffold(
                        onNavigate = navController::navigate,
                        navBackStackEntry = navBackStackEntry,
                        snackBarHostState = snackBarHostState,
                        modifier = Modifier.fillMaxSize(),
                        showBottomBar = shouldShowBottomBar(navBackStackEntry),
                    ) {
                        startDestination.value?.let { startDestination ->
                            Navigation(
                                navController = navController,
                                snackbarHostState = snackBarHostState,
                                startDestination = startDestination,
                                onDataLoaded = {
                                    splashViewModel.onEvent(SplashEvent.SplashLoadingComplete)
                                },
                                imageLoader = imageLoader,
                                workProposalViewModel = workProposalViewModel,
                                isWorker = Session.userSession.value?.isWorker ?: false
                            )
                        }
                    }
                }
            }
        }
        if (!checkForLocationPermission()) {
            showLocationDisclosureDialog()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        chatSocketUseCases.closeConnection()
    }

    private fun checkForLocationPermission(): Boolean {
        // Check if the location permission is granted
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun askForLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    private fun showLocationDisclosureDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Location Access Disclosure")
        builder.setMessage(getString(R.string.this_app_uses_your_location))
        builder.setPositiveButton("Okey") { dialog, which ->
            // Handle the logic when the user agrees to share the location
            dialog.dismiss()
            if (!checkForLocationPermission()) {
                askForLocationPermission()
            }
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
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