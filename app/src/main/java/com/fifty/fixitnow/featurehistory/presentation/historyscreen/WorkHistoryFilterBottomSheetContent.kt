package com.fifty.fixitnow.featurehistory.presentation.historyscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.component.HorizontalDivider
import com.fifty.fixitnow.core.presentation.component.PrimaryButton
import com.fifty.fixitnow.core.presentation.component.SecondaryHeader
import com.fifty.fixitnow.core.presentation.ui.theme.LargeButtonHeight
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.util.formatToDateWithDay
import com.fifty.fixitnow.featureworker.presentation.component.Chip
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.maxkeppeker.sheets.core.models.base.UseCaseState

@Composable
fun WorkHistoryFilterBottomSheetContent(
    viewModel: WorkHistoryViewModel,
    calenderState: UseCaseState,
    onClearAllClick: () -> Unit,
    onApplyClick: () -> Unit
) {
    val filterState = viewModel.tempFilterState.value
    Column(Modifier.fillMaxWidth()) {
        SecondaryHeader(
            modifier = Modifier.padding(SizeMedium),
            text = stringResource(R.string.filter_work_history),
            style = MaterialTheme.typography.titleMedium
        )
        HorizontalDivider()
        SecondaryHeader(
            modifier = Modifier
                .padding(SizeMedium),
            text = stringResource(R.string.choose_date_range),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Normal
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SizeMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "From Date: ",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    Spacer(modifier = Modifier.width(SizeMedium))
                    Text(
                        text = viewModel.tempSelectedRange.value.start.formatToDateWithDay(),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Medium
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(SizeMedium))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "To Date: ",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    )
                    Spacer(modifier = Modifier.width(SizeMedium))
                    Text(
                        text = viewModel.tempSelectedRange.value.endInclusive.formatToDateWithDay(),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Medium
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Spacer(modifier = Modifier.width(SizeMedium))
            IconButton(onClick = {
                calenderState.show()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_calander),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        Spacer(modifier = Modifier.height(SizeMedium))
        HorizontalDivider()
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SizeMedium),
            mainAxisAlignment = MainAxisAlignment.Start,
            mainAxisSpacing = SizeMedium,
            crossAxisSpacing = SizeMedium
        ) {
            Chip(
                selected = filterState.hiringHistory,
                onChipClick = {
                    viewModel.onEvent(WorkHistoryEvent.ToggleHiringHistory)
                },
                text = "Hiring History"
            )
            Chip(
                selected = filterState.workHistory,
                onChipClick = {
                    viewModel.onEvent(WorkHistoryEvent.ToggleWorkHistory)
                },
                text = "Work History"
            )
        }
        HorizontalDivider()
        SecondaryHeader(
            modifier = Modifier
                .padding(SizeMedium),
            text = stringResource(R.string.filter),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Normal
        )
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SizeMedium),
            mainAxisAlignment = MainAxisAlignment.Start,
            mainAxisSpacing = SizeMedium,
            crossAxisSpacing = SizeMedium
        ) {
            Chip(
                selected = filterState.pendingWorks,
                onChipClick = {
                    viewModel.onEvent(WorkHistoryEvent.TogglePendingFilter)
                },
                text = "Pending"
            )
            Chip(
                selected = filterState.completedWorks,
                onChipClick = {
                    viewModel.onEvent(WorkHistoryEvent.ToggleCompletedFilter)
                },
                text = "Completed"
            )
            Chip(
                selected = filterState.cancelledWorks,
                onChipClick = {
                    viewModel.onEvent(WorkHistoryEvent.ToggleCancelledFilter)
                },
                text = "Cancelled"
            )
        }
        Spacer(modifier = Modifier.height(SizeMedium))
        HorizontalDivider()
        Row(
            modifier = Modifier.padding(SizeMedium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                onClick = onClearAllClick,
                modifier = Modifier
                    .weight(0.5f)
                    .height(LargeButtonHeight)
            ) {
                Text(
                    text = stringResource(R.string.clear_all),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
            Spacer(modifier = Modifier.width(SizeMedium))
            PrimaryButton(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.apply),
                onClick = onApplyClick
            )
        }
    }
}