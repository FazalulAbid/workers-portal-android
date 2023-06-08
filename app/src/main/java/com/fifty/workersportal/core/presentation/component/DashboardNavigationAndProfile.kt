package com.fifty.workersportal.core.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.workersportal.core.presentation.ui.theme.SizeLarge
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SmallProfilePictureHeight

@Composable
fun DashboardNavigationAndProfile(

) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(SizeLarge),
                    painter = painterResource(id = R.drawable.ic_near_me),
                    contentDescription = stringResource(R.string.selected_address),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(SizeExtraSmall))
                Text(
                    text = "Ernakulam",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_expand_more),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            Text(
                text = "Vikas Nagas, Maradu, Ernakulam",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
        Spacer(modifier = Modifier.width(SizeMedium))
        Image(
            modifier = Modifier.size(SmallProfilePictureHeight),
            painter = painterResource(id = R.drawable.test_man_profile),
            contentDescription = stringResource(R.string.profile_picture)
        )
    }
}