package com.fifty.workersportal.featureworker.presentation.workerdashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import coil.ImageLoader
import com.fifty.workersportal.R
import com.fifty.workersportal.core.domain.util.Session
import com.fifty.workersportal.core.presentation.component.DashboardSelectedAddressAndProfile
import com.fifty.workersportal.core.presentation.component.HorizontalDivider
import com.fifty.workersportal.core.presentation.component.PrimaryHeader
import com.fifty.workersportal.core.presentation.component.SecondaryHeader
import com.fifty.workersportal.core.presentation.ui.theme.ScaffoldBottomPaddingValue
import com.fifty.workersportal.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SmallStrokeThickness
import com.fifty.workersportal.core.presentation.util.OnLifecycleEvent
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.presentation.util.makeToast
import com.fifty.workersportal.core.util.Screen
import com.fifty.workersportal.featureuser.presentation.userdashboard.UserDashboardEvent
import com.fifty.workersportal.featureworker.presentation.component.OpenToWorkSwitch
import com.fifty.workersportal.featureworker.presentation.component.WorkProposalListItem
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WorkerDashboardScreen(
    imageLoader: ImageLoader,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: WorkerDashboardViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.MakeToast -> {
                    makeToast(R.string.oops_something_went_wrong, context)
                }

                UiEvent.NavigateUp -> {
                    onNavigateUp()
                }

                else -> Unit
            }
        }
    }

    OnLifecycleEvent { owner, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                viewModel.onEvent(WorkerDashboardEvent.UpdateSelectedAddress)
            }

            else -> Unit
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = ScaffoldBottomPaddingValue),
        color = MaterialTheme.colorScheme.background
    ) {
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    modifier = Modifier.padding(
                        top = SizeMedium,
                        start = SizeMedium,
                        end = SizeMedium,
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    DashboardSelectedAddressAndProfile(
                        profileImageUrl = Session.userSession.value?.profilePicture ?: "",
                        imageLoader = imageLoader,
                        onProfileClick = {
                            onNavigate(Screen.WorkerProfileScreen.route)
                        },
                        localAddress = state.selectedLocalAddress,
                        onLocationClick = {
                            onNavigate(Screen.SelectLocationScreen.route)
                        }
                    )
                    PrimaryHeader(text = stringResource(R.string.worker_dashboard))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(SizeExtraSmall))
                    OpenToWorkSwitch(
                        modifier = Modifier.fillMaxWidth(),
                        checked = state.profile?.openToWork == true,
                        onCheckedChange = {
                            viewModel.onEvent(WorkerDashboardEvent.ToggleOpenToWork(it))
                        }
                    )
                    Spacer(modifier = Modifier.height(SizeExtraSmall))
                    HorizontalDivider()
                    SecondaryHeader(
                        modifier = Modifier.padding(
                            vertical = SizeMedium
                        ),
                        text = stringResource(R.string.work_proposals),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                LazyColumn() {
                    items(20) {
                        WorkProposalListItem()
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = SizeMedium),
                            color = MaterialTheme.colorScheme.surface,
                            thickness = SmallStrokeThickness
                        )
                    }
                }
            }
        }
    }

}