package com.fifty.workersportal.featureuser.presentation.userdashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.util.Screen

@Composable
fun UserDashboardScreen(
    onNavigate: (String) -> Unit = {},
    viewModel: UserDashboardViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val list = listOf(
            Screen.WorkerDashboardScreen.route,
            Screen.SelectWorkerCategoryScreen.route,
            Screen.WorkerListScreen.route,
            Screen.WorkerProfileScreen.route,
            Screen.RegisterAsWorkerScreen.route,
            Screen.MessageScreen.route,
        )
        LazyHorizontalGrid(
            rows = GridCells.Adaptive(minSize = 70.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(list) {
                Button(onClick = {
                    onNavigate(it)
                }, Modifier.padding(SizeMedium)) {
                    Text(text = it)
                }
            }
        }
    }
}