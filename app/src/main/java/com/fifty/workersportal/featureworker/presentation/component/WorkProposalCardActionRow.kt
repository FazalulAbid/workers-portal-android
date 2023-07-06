package com.fifty.workersportal.featureworker.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.ui.theme.DarkGreenColor
import com.fifty.workersportal.core.presentation.ui.theme.RedColor
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium

@Composable
fun WorkProposalCardActionRow(
    actionIconSize: Dp,
    onAccept: () -> Unit,
    onReject: () -> Unit,
) {
    Row(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        IconButton(
            modifier = Modifier
                .size(actionIconSize)
                .background(DarkGreenColor),
            onClick = onAccept,
            content = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_done),
                    tint = MaterialTheme.colorScheme.background,
                    contentDescription = stringResource(R.string.accept_proposal),
                )
            }
        )
        Spacer(modifier = Modifier.width(SizeMedium))
        IconButton(
            modifier = Modifier
                .size(actionIconSize)
                .background(RedColor),
            onClick = onReject,
            content = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    tint = MaterialTheme.colorScheme.background,
                    contentDescription = stringResource(R.string.reject_proposal),
                )
            },
        )
    }
}