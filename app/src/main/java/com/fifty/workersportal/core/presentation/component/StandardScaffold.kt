package com.fifty.workersportal.core.presentation.component

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavOptions
import com.fifty.workersportal.core.presentation.ui.theme.ScaffoldBottomPaddingValue

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StandardScaffold(
    onNavigate: (route: String, navOptions: NavOptions) -> Unit,
    navBackStackEntry: NavBackStackEntry?,
    modifier: Modifier = Modifier,
    showBottomBar: Boolean = true,
    snackBarHostState: SnackbarHostState,
    content: @Composable () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            StandardBottomBar(
                onNavigate = onNavigate,
                navBackStackEntry = navBackStackEntry,
                showBottomBar = showBottomBar,
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) {
        ScaffoldBottomPaddingValue = it.calculateBottomPadding()
        content()
    }
}