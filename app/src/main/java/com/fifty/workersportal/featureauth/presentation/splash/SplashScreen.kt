package com.fifty.workersportal.featureauth.presentation.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Composable
fun SplashScreen(
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    onPopBackStack: () -> Unit = {},
    onNavigate: (String) -> Unit = {},
    viewModel: SplashViewModel = hiltViewModel()
) {
    val scale = remember {
        Animatable(0f)
    }
    val overshootInterpolator = remember {
        OvershootInterpolator(2f)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(SizeMedium),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "")
    }
}