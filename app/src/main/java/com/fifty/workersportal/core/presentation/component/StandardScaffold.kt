package com.fifty.workersportal.core.presentation.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import com.fifty.workersportal.R
import com.fifty.workersportal.core.domain.model.BottomNavItem
import com.fifty.workersportal.core.presentation.ui.theme.SizeExtraLarge
import com.fifty.workersportal.core.presentation.ui.theme.SizeLarge
import com.fifty.workersportal.core.util.NavigationItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StandardScaffold(
    navController: NavController,
    modifier: Modifier = Modifier,
    showBottomBar: Boolean = true,
    snackBarHostState: SnackbarHostState,
    bottomNavItems: List<BottomNavItem> = listOf(
        BottomNavItem(
            route = NavigationItem.Home.label,
            label = stringResource(R.string.home),
            icon = painterResource(id = R.drawable.ic_home),
            iconSelected = painterResource(id = R.drawable.ic_home_filled),
            contentDescription = stringResource(R.string.home)
        ),
        BottomNavItem(
            route = NavigationItem.Work.label,
            label = stringResource(R.string.work),
            icon = painterResource(id = R.drawable.ic_handshake),
            iconSelected = painterResource(id = R.drawable.ic_handshake_filled),
            contentDescription = stringResource(R.string.work)
        ),
        BottomNavItem(
            route = NavigationItem.Favorite.label,
            label = stringResource(R.string.favorites),
            icon = painterResource(id = R.drawable.ic_favorite),
            iconSelected = painterResource(id = R.drawable.ic_favorite_filled),
            contentDescription = stringResource(R.string.favorites)
        ),
        BottomNavItem(
            route = NavigationItem.History.label,
            label = stringResource(R.string.history),
            icon = painterResource(id = R.drawable.ic_history),
            iconSelected = painterResource(id = R.drawable.ic_history),
            contentDescription = stringResource(R.string.history)
        ),
    ),
    content: @Composable () -> Unit
) {
    val selectedItem = remember { mutableStateOf(0) }
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val parentRouteName = navBackStackEntry.value?.destination?.parent?.route

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.background
            ) {
                bottomNavItems.forEachIndexed { index, item ->
                    val isSelected = parentRouteName == item.route
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primary,
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurface
                        ),
                        selected = isSelected,
                        alwaysShowLabel = isSelected,
                        onClick = {
                            selectedItem.value = index
                            navController.navigate(item.route, navOptions {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            })
                        },
                        icon = {
                            Icon(
                                painter = if (isSelected) {
                                    item.iconSelected
                                } else item.icon,
                                modifier = if (isSelected) {
                                    Modifier.size(SizeLarge)
                                } else Modifier.size(SizeExtraLarge),
                                contentDescription = item.contentDescription
                            )
                        },
                        label = {
                            Text(
                                text = item.label,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    )
                }
            }
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            content()
        }
    }
}