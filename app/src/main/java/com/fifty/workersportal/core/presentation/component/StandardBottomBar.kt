package com.fifty.workersportal.core.presentation.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.fifty.workersportal.R
import com.fifty.workersportal.core.domain.model.BottomNavItem
import com.fifty.workersportal.core.presentation.ui.theme.SizeExtraLarge
import com.fifty.workersportal.core.presentation.ui.theme.SizeLarge
import com.fifty.workersportal.core.util.NavigationParent
import com.fifty.workersportal.core.util.Screen

@Composable
fun StandardBottomBar(
    onNavigate: (route: String, navOptions: NavOptions) -> Unit,
    navBackStackEntry: NavBackStackEntry?,
    isWorker: Boolean,
    showBottomBar: Boolean = true
) {
    val bottomNavItems: List<BottomNavItem> = listOf(
        BottomNavItem(
            route = NavigationParent.Home.route,
            label = stringResource(R.string.home),
            icon = painterResource(id = R.drawable.ic_home),
            iconSelected = painterResource(id = R.drawable.ic_home_filled),
            contentDescription = stringResource(R.string.home)
        ),
        BottomNavItem(
            route = NavigationParent.Work.route,
            label = stringResource(R.string.work),
            icon = painterResource(id = R.drawable.ic_handshake),
            iconSelected = painterResource(id = R.drawable.ic_handshake_filled),
            contentDescription = stringResource(R.string.work)
        ),
        BottomNavItem(
            route = NavigationParent.Favorite.route,
            label = stringResource(R.string.favorites),
            icon = painterResource(id = R.drawable.ic_favorite),
            iconSelected = painterResource(id = R.drawable.ic_favorite_filled),
            contentDescription = stringResource(R.string.favorites)
        ),
        BottomNavItem(
            route = NavigationParent.History.route,
            label = stringResource(R.string.history),
            icon = painterResource(id = R.drawable.ic_history),
            iconSelected = painterResource(id = R.drawable.ic_history),
            contentDescription = stringResource(R.string.history)
        ),
    )
    val selectedItem = remember { mutableStateOf(0) }
    val parentRouteName = navBackStackEntry?.destination?.parent?.route

    if (showBottomBar) {
        NavigationBar(
            modifier = Modifier.shadow(
                elevation = SizeExtraLarge
            ),
            containerColor = MaterialTheme.colorScheme.background
        ) {
            bottomNavItems.forEachIndexed { index, item ->
                val isSelected = parentRouteName == item.route
                NavigationBarItem(
                    colors = NavigationBarItemDefaults.colors(
                        selectedTextColor = MaterialTheme.colorScheme.onSurface,
                        indicatorColor = MaterialTheme.colorScheme.primary,
                        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurface
                    ),
                    selected = isSelected,
                    alwaysShowLabel = isSelected,
                    onClick = {
                        selectedItem.value = index
                        val destinationRoute = when (item.route) {
                            NavigationParent.Work.route -> if (isWorker) {
                                Screen.WorkerDashboardScreen.route
                            } else {
                                Screen.RegisterAsWorkerScreen.route
                            }

                            else -> item.route
                        }
                        onNavigate(destinationRoute, navOptions {
                            popUpTo(Screen.UserDashboardScreen.route) {
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
}