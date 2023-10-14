package com.fifty.fixitnow.core.presentation.component

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.domain.model.BottomNavItem
import com.fifty.fixitnow.core.presentation.ui.theme.SizeExtraLarge
import com.fifty.fixitnow.core.presentation.ui.theme.SizeLarge
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.util.NavigationParent
import com.fifty.fixitnow.core.util.Screen

@Composable
fun StandardBottomBar(
    onNavigate: (route: String, navOptions: NavOptions) -> Unit,
    navBackStackEntry: NavBackStackEntry?,
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
//        BottomNavItem(
//            route = NavigationParent.Chat.route,
//            label = stringResource(R.string.chats),
//            icon = painterResource(id = R.drawable.ic_chat),
//            iconSelected = painterResource(id = R.drawable.ic_chat_filled),
//            contentDescription = stringResource(R.string.chats)
//        ),
        BottomNavItem(
            route = NavigationParent.History.route,
            label = stringResource(R.string.history),
            icon = painterResource(id = R.drawable.ic_history),
            iconSelected = painterResource(id = R.drawable.ic_history_filled),
            contentDescription = stringResource(R.string.history)
        ),
    )
    val selectedItem = remember { mutableStateOf(0) }
    val parentRouteName = navBackStackEntry?.destination?.parent?.route

    if (showBottomBar) {
        NavigationBar(
            modifier = Modifier
                .shadow(elevation = SizeExtraLarge),
            containerColor = MaterialTheme.colorScheme.background
        ) {
            Row(modifier = Modifier.padding(SizeMedium)) {
                bottomNavItems.forEachIndexed { index, item ->
                    val isSelected = parentRouteName == item.route

                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = MaterialTheme.colorScheme.background,
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurface
                        ),
                        selected = isSelected,
                        onClick = {
                            selectedItem.value = index
                            onNavigate(item.route, navOptions {
                                popUpTo(Screen.UserDashboardScreen.route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            })
                        },
                        icon = {
                            Crossfade(
                                targetState = isSelected,
                                animationSpec = tween(durationMillis = 1000)
                            ) { isSelected ->
                                Icon(
                                    painter = if (isSelected) {
                                        item.iconSelected
                                    } else item.icon,
                                    modifier = Modifier.size(SizeExtraLarge),
                                    contentDescription = item.contentDescription
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}