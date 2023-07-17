package com.fifty.fixitnow.core.presentation.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.fifty.fixitnow.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StandardAppBar(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.background,
    onNavigateUp: () -> Unit = {},
    showBackArrow: Boolean = false,
    navActions: @Composable RowScope.() -> Unit = {},
    title: @Composable () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults
            .centerAlignedTopAppBarColors(
                containerColor = containerColor
            ),
        title = title,
        navigationIcon = {
            if (showBackArrow) {
                IconButton(onClick = { onNavigateUp() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back_),
                        contentDescription = stringResource(R.string.go_to_previous_screen),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        actions = navActions
    )
}