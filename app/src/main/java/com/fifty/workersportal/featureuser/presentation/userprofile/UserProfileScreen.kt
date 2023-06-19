package com.fifty.workersportal.featureuser.presentation.userprofile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.HorizontalDivider
import com.fifty.workersportal.core.presentation.component.StandardAppBar
import com.fifty.workersportal.core.presentation.ui.theme.ExtraExtraLargeProfilePictureHeight
import com.fifty.workersportal.core.presentation.ui.theme.LargeStrokeThickness
import com.fifty.workersportal.core.presentation.ui.theme.MediumStrokeThickness
import com.fifty.workersportal.core.presentation.ui.theme.SizeExtraLarge
import com.fifty.workersportal.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.workersportal.core.presentation.ui.theme.SizeLarge
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.util.Screen
import com.maxkeppeker.sheets.core.CoreDialog
import com.maxkeppeker.sheets.core.models.CoreSelection
import com.maxkeppeker.sheets.core.models.base.ButtonStyle
import com.maxkeppeker.sheets.core.models.base.Header
import com.maxkeppeker.sheets.core.models.base.SelectionButton
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    onNavigate: (String) -> Unit,
    onNavigateWithPopBackStack: (String) -> Unit,
    onNavigateUp: () -> Unit,
    viewModel: UserProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.state.value
    val screenWidth = with(LocalConfiguration.current) { screenWidthDp.dp }
    val logoutDialogState = rememberUseCaseState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.OnLogout -> {
                    onNavigateWithPopBackStack(Screen.AuthScreen.route)
                }

                else -> Unit
            }
        }
    }

    LazyColumn {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SizeMedium),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                StandardAppBar(
                    onNavigateUp = onNavigateUp,
                    showBackArrow = true,
                    title = {
                        Text(
                            text = stringResource(R.string.profile),
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                )
                Spacer(modifier = Modifier.height(SizeMedium))
                Box(
                    modifier = Modifier
                        .border(
                            width = LargeStrokeThickness,
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primary
                        )
                        .padding(SizeSmall)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.plumber_profile),
                        contentDescription = "Plumber description",
                        Modifier
                            .size(ExtraExtraLargeProfilePictureHeight)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.height(SizeMedium))
                Text(
                    modifier = Modifier.widthIn(max = screenWidth * 0.75f),
                    text = "Fazalul Abid",
//            text = "${state.profile?.firstName} ${state.profile?.lastName}",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(SizeSmall))
                Text(
                    text = "fazalulabid007@gmail.com",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(SizeLarge))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.shapes.medium
                        )
                ) {
                    Column {
                        UserProfileButton(
                            icon = painterResource(id = R.drawable.ic_edit),
                            text = "Edit profile",
                            description = "Tap the Edit Profile button to modify and update your personal information"
                        ) {
                            onNavigate(Screen.EditUserProfileScreen.route)
                        }
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.background,
                            thickness = MediumStrokeThickness
                        )
                        UserProfileButton(
                            icon = painterResource(id = R.drawable.ic_favorite_filled),
                            text = "Favorites",
                            description = "Tap the Favorite button to quickly navigate to your list of favored workers and manage them with ease."
                        ) {
                            onNavigate(Screen.FavoriteScreen.route)
                        }
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.background,
                            thickness = MediumStrokeThickness
                        )
                        UserProfileButton(
                            icon = painterResource(id = R.drawable.ic_history),
                            text = "History",
                            description = "Tap the Work History button to view your previous work activities or job history."
                        ) {
                            onNavigate(Screen.HistoryScreen.route)
                        }
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.background,
                            thickness = MediumStrokeThickness
                        )
                        UserProfileButton(
                            icon = painterResource(id = R.drawable.ic_settings),
                            text = "Settings",
                            description = "Tap the Settings button to access and adjust preferences and configurations for your app."
                        ) {

                        }
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.background,
                            thickness = MediumStrokeThickness
                        )
                        UserProfileButton(
                            icon = painterResource(id = R.drawable.ic_logout),
                            text = "Logout",
                            description = "Tap the Logout button to securely sign out and exit the current session."
                        ) {
                            logoutDialogState.show()
                        }
                    }
                }
            }
        }
    }

    CoreDialog(
        state = logoutDialogState,
        selection = CoreSelection(
            withButtonView = true,
            negativeButton = SelectionButton(
                text = "Cancel",
                type = ButtonStyle.TEXT
            ),
            positiveButton = SelectionButton(
                text = "Logout",
                type = ButtonStyle.FILLED
            ),
            onPositiveClick = {
                viewModel.onEvent(UserProfileEvent.Logout)
            }
        ),
        header = Header.Default(
            "Logout"
        ),
        onPositiveValid = true,
        body = {
            Text(
                text = stringResource(R.string.are_you_sure_you_want_to_log_out),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    )
}

@Composable
fun UserProfileButton(
    modifier: Modifier = Modifier,
    icon: Painter,
    text: String,
    description: String = "",
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable {
                onClick()
            }
            .padding(SizeMedium)
    ) {
        Icon(
            modifier = Modifier.size(SizeExtraLarge),
            painter = icon, contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(SizeMedium))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Medium
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(SizeExtraSmall))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Normal
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}