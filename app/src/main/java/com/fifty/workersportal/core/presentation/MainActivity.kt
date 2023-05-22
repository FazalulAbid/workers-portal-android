package com.fifty.workersportal.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.fifty.workersportal.core.presentation.component.Navigation
import com.fifty.workersportal.core.presentation.ui.theme.WorkersPortalTheme
import com.fifty.workersportal.featureauth.presentation.splash.SplashViewModel
import com.google.accompanist.insets.ProvideWindowInsets

class MainActivity : ComponentActivity() {

    private val splashScreenViewModel: SplashViewModel by viewModels()

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
                    Navigation()
                }
            }
        }
    }
}