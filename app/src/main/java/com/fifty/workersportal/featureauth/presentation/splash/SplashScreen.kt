package com.fifty.workersportal.featureauth.presentation.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Composable
fun SplashScreen(
    dispatcher: CoroutineDispatcher = Dispatchers.Main
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(SizeMedium),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "")

    }
}