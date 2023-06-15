package com.fifty.workersportal.featurelocation.presentation.selectlocation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.SecondaryHeader
import com.fifty.workersportal.core.presentation.component.StandardAppBar
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall
import com.fifty.workersportal.core.util.Screen
import com.fifty.workersportal.featurelocation.presentation.component.DetectLocationButton
import com.fifty.workersportal.featurelocation.presentation.component.SavedAddressItem

@Composable
fun SelectLocationScreen(
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: SelectLocationViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StandardAppBar(
            onNavigateUp = onNavigateUp,
            showBackArrow = true,
            title = {
                Text(
                    text = stringResource(R.string.select_a_location),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        )
        LazyColumn {
            item {
                Column {
                    DetectLocationButton(
                        text = stringResource(R.string.detect_your_current_location),
                        description = stringResource(R.string.detect_your_current_location),
                        onClick = {
                            onNavigate(Screen.DetectCurrentLocationScreen.route)
                        }
                    )
                    Spacer(modifier = Modifier.height(SizeMedium))
                    SecondaryHeader(
                        modifier = Modifier.padding(
                            horizontal = SizeMedium,
                            vertical = SizeSmall
                        ),
                        text = stringResource(R.string.saved_addresses),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            items(state.localAddresses) { localAddress ->
                SavedAddressItem(
                    localAddress = localAddress,
                    onClick = { },
                    onMoreClick = { },
                    onShareClick = {}
                )
            }
        }
    }
}