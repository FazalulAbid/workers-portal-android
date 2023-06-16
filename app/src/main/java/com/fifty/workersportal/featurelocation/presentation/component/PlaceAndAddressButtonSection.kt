package com.fifty.workersportal.featurelocation.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.PrimaryButton
import com.fifty.workersportal.core.presentation.ui.theme.LargeButtonHeight
import com.fifty.workersportal.core.presentation.ui.theme.SizeLarge
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall
import com.fifty.workersportal.core.presentation.util.shimmerEffect
import com.fifty.workersportal.featurelocation.presentation.detectcurrentlocation.DetectCurrentLocationState
import com.google.maps.android.compose.CameraPositionState

@Composable
fun PlaceAndAddressButtonSection(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState,
    state: DetectCurrentLocationState,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(SizeMedium)
            .clip(RoundedCornerShape(topStart = SizeSmall, topEnd = SizeSmall))
    ) {
        if (state.isLoading || cameraPositionState.isMoving) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .shimmerEffect(),
                text = "",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(modifier = Modifier.height(SizeSmall))
            Text(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .shimmerEffect(),
                text = "",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(SizeMedium))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(LargeButtonHeight)
                    .shimmerEffect(),
            )
        } else {
            Row() {
                Icon(
                    modifier = Modifier.size(SizeLarge),
                    painter = painterResource(id = R.drawable.ic_location_mark),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(SizeSmall))
                Column {
                    Text(
                        text = if (state.localAddress?.place?.isBlank() == true) {
                            state.localAddress.subLocality ?: state.localAddress.city ?: ""
                        } else state.localAddress?.place ?: "",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Spacer(modifier = Modifier.height(SizeSmall))
                    Text(
                        text = ("${state.localAddress?.subLocality}, " +
                                "${state.localAddress?.city}"),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(SizeMedium))
            PrimaryButton(
                text = stringResource(R.string.enter_complete_address),
                onClick = onClick
            )
        }
    }
}