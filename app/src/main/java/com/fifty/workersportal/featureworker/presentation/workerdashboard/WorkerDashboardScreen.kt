package com.fifty.workersportal.featureworker.presentation.workerdashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import com.fifty.workersportal.core.presentation.component.DashboardNavigationAndProfile
import com.fifty.workersportal.core.presentation.component.HorizontalDivider
import com.fifty.workersportal.core.presentation.component.PrimaryHeader
import com.fifty.workersportal.core.presentation.component.SecondaryHeader
import com.fifty.workersportal.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.workersportal.core.presentation.ui.theme.SizeLarge
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SmallProfilePictureHeight
import com.fifty.workersportal.core.presentation.ui.theme.SmallStrokeThickness
import com.fifty.workersportal.featureworker.presentation.component.OpenToWorkSwitch
import com.fifty.workersportal.featureworker.presentation.component.WorkProposalListItem

@Composable
fun WorkerDashboardScreen(
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {}
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
            DashboardNavigationAndProfile()
            PrimaryHeader(text = stringResource(R.string.worker_dashboard))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(SizeExtraSmall))
            OpenToWorkSwitch(
                modifier = Modifier.fillMaxWidth(),
                checked = false,
                onCheckedChange = {}
            )
            Spacer(modifier = Modifier.height(SizeExtraSmall))
            HorizontalDivider()
            SecondaryHeader(
                text = stringResource(R.string.work_proposals)
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