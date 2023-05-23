package com.fifty.workersportal.core.presentation.component

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun StandardAppBar(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit = {},
    showBackArrow: Boolean = false,
    navActions: @Composable RowScope.() -> Unit = {},
    title: @Composable () -> Unit = {}
) {
//    TopAppBar(
//        title = title,
//        modifier = modifier,
//        navigationIcon = {
//            if (showBackArrow) {
//                IconButton(onClick = { onNavigateUp() }) {
//                    Icon(painter = , contentDescription = )
//                }
//            }
//        }
//    ) {
//
//    }

}