package com.fifty.workersportal.featureworker.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.ui.theme.PastelGreen
import com.fifty.workersportal.core.presentation.ui.theme.PastelRed
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall

@Composable
fun WorkProposalCardActionRow(
    actionIconSize: Dp,
    onAccept: () -> Unit,
    onReject: () -> Unit,
) {
    Row(
        Modifier
            .padding(horizontal = SizeMedium)
            .fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier
                .size(actionIconSize),
            onClick = onAccept,
            colors = IconButtonDefaults.iconButtonColors(containerColor = PastelGreen),
            content = {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    tint = MaterialTheme.colorScheme.background,
                    contentDescription = stringResource(R.string.accept_proposal),
                )
            }
        )
        Spacer(modifier = Modifier.width(SizeSmall))
        IconButton(
            modifier = Modifier
                .size(actionIconSize),
            onClick = onReject,
            colors = IconButtonDefaults.iconButtonColors(containerColor = PastelRed),
            content = {
                Icon(
                    imageVector = Icons.Rounded.Close,
                    tint = MaterialTheme.colorScheme.background,
                    contentDescription = stringResource(R.string.reject_proposal),
                )
            },
        )
        Spacer(modifier = Modifier.width(SizeSmall))
    }
}