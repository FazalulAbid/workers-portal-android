package com.fifty.fixitnow.featureauth.presentation.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.fifty.fixitnow.core.util.Screen

@Composable
fun OnBoardingScreen(
    onNavigate: (String) -> Unit,
    popBackStack: () -> Unit,
    imageLoader: ImageLoader,
    viewModel: OnBoardingViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
            viewModel.saveOnBoardingState(completed = true)
            popBackStack()
            onNavigate(Screen.AuthScreen.route)
        }) {
            Text(
                text = "On Boarding Screen",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}