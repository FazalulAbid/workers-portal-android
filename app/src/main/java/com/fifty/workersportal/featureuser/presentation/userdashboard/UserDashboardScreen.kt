package com.fifty.workersportal.featureuser.presentation.userdashboard

import android.Manifest
import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.CoarseLocationPermissionTextProvider
import com.fifty.workersportal.core.presentation.component.DashboardNavigationAndProfile
import com.fifty.workersportal.core.presentation.component.HorizontalDivider
import com.fifty.workersportal.core.presentation.component.PermissionDialog
import com.fifty.workersportal.core.presentation.component.SecondaryHeader
import com.fifty.workersportal.core.presentation.component.StandardTextField
import com.fifty.workersportal.core.presentation.ui.theme.MediumButtonHeight
import com.fifty.workersportal.core.presentation.ui.theme.MediumProfilePictureHeight
import com.fifty.workersportal.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.util.Screen
import com.fifty.workersportal.core.util.openAppSettings
import com.fifty.workersportal.featureuser.presentation.component.AutoSlidingCarousal
import com.fifty.workersportal.featureuser.presentation.component.DashboardGreetingText
import com.fifty.workersportal.featureuser.presentation.component.SuggestedCategoryItem
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalPagerApi::class)
@Composable
fun UserDashboardScreen(
    onNavigate: (String) -> Unit = {},
    imageLoader: ImageLoader,
    viewModel: UserDashboardViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val permissionsToRequest = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
    )

    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            permissionsToRequest.forEach { permission ->
                viewModel.onPermissionResult(
                    permission = permission,
                    isGranted = perms[permission] == true
                )
            }
        }
    )

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    onNavigate(event.route)
                }

                else -> {}
            }

        }
    }

    LazyColumn {
        item {
            Column(
                modifier = Modifier.padding(SizeMedium)
            ) {
                DashboardGreetingText(
                    name = "Fazalul",
                    greetingText = stringResource(R.string.dashboard_greeting_prefix)
                )
                Spacer(Modifier.height(SizeExtraSmall))
                DashboardNavigationAndProfile(
                    onProfileClick = {
                        onNavigate(Screen.WorkerProfileScreen.route)
                    },
                    onLocationClick = {
                        multiplePermissionResultLauncher.launch(
                            permissionsToRequest
                        )
                    }
                )
                Spacer(modifier = Modifier.height(SizeMedium))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(SizeMedium))
                StandardTextField(
                    basicTextFieldModifier = Modifier
                        .fillMaxWidth()
                        .height(MediumButtonHeight),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = stringResource(R.string.search_for_worker_category),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    trailingIcon = null,
                    hint = stringResource(R.string.search_plumber),
                    value = "",
                    onValueChange = {

                    }
                )
            }
        }
        item {
            Column {
                Spacer(modifier = Modifier.height(SizeSmall))
                AutoSlidingCarousal(
                    banners = viewModel.bannersState.value.banners,
                    imageLoader = imageLoader
                )
                Spacer(modifier = Modifier.height(SizeSmall))
            }
        }
        item {
            Column(Modifier.padding(horizontal = SizeMedium)) {
                SecondaryHeader(
                    text = stringResource(R.string.suggested_categories),
                    modifier = Modifier.padding(vertical = SizeMedium),
                    style = MaterialTheme.typography.titleMedium,
                    moreOption = true,
                    moreOptionText = stringResource(R.string.all_categories),
                    onMoreOptionClick = {
                        onNavigate(Screen.SearchCategoryScreen.route)
                    }
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyRow(Modifier.fillMaxWidth()) {
                    items(viewModel.suggestedCategoriesState.value.suggestedCategories) {
                        SuggestedCategoryItem(
                            category = it,
                            imageLoader = imageLoader,
                            imageSize = MediumProfilePictureHeight
                        )
                    }
                }
                Spacer(modifier = Modifier.height(SizeSmall))
            }
        }
    }

    viewModel.visiblePermissionDialogQueue.reversed().forEach { permission ->
        PermissionDialog(
            permissionTextProvider = when (permission) {
                Manifest.permission.ACCESS_COARSE_LOCATION -> CoarseLocationPermissionTextProvider()
                else -> {
                    return@forEach
                }
            },
            isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                context as Activity,
                permission
            ),
            onDismiss = viewModel::dismissPermissionDialog,
            onOkClick = {
                viewModel.dismissPermissionDialog()
                multiplePermissionResultLauncher.launch(
                    arrayOf(permission)
                )
            },
            onGotoAppSettingsClick = {
                context.openAppSettings()
                viewModel.dismissPermissionDialog()
            }
        )
    }
}