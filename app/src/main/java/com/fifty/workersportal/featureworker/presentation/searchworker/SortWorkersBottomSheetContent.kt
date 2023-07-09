package com.fifty.workersportal.featureworker.presentation.searchworker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.HorizontalDivider
import com.fifty.workersportal.core.presentation.component.PrimaryButton
import com.fifty.workersportal.core.presentation.component.SecondaryHeader
import com.fifty.workersportal.core.presentation.ui.theme.LargeButtonHeight
import com.fifty.workersportal.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium

@Composable
fun SortWorkersBottomSheetContent(
    viewModel: SearchWorkerViewModel,
    onClearAllClick: () -> Unit,
    onApplyClick: () -> Unit
) {
    val sortState = viewModel.tempSortState.value
    Column(Modifier.fillMaxWidth()) {
        SecondaryHeader(
            modifier = Modifier.padding(SizeMedium),
            text = stringResource(R.string.sort),
            style = MaterialTheme.typography.titleMedium
        )
        HorizontalDivider()
        SortWorkerRadioItem(
            isSelected = (!sortState.isRatingHighToLow && !sortState.isWageHighToLow &&
                    !sortState.isDistanceLowToHigh && !sortState.isWageLowToHigh),
            text = "Relevance",
            onClick = { viewModel.onEvent(SearchWorkerEvent.SelectRelevance) }
        )
        SortWorkerRadioItem(
            isSelected = sortState.isRatingHighToLow,
            text = "Rating: High to Low",
            onClick = { viewModel.onEvent(SearchWorkerEvent.SelectRatingHighToLowSort) }
        )
        SortWorkerRadioItem(
            isSelected = sortState.isDistanceLowToHigh,
            text = "Distance: Low to High",
            onClick = { viewModel.onEvent(SearchWorkerEvent.SelectDistanceLowToHighSort) }
        )
        SortWorkerRadioItem(
            isSelected = sortState.isWageLowToHigh,
            text = "Wage: Low to High",
            onClick = { viewModel.onEvent(SearchWorkerEvent.SelectCostLowToHighSort) }
        )
        SortWorkerRadioItem(
            isSelected = sortState.isWageHighToLow,
            text = "Wage: High to Low",
            onClick = { viewModel.onEvent(SearchWorkerEvent.SelectCostHighToLowSort) }
        )
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

@Composable
fun SortWorkerRadioItem(
    isSelected: Boolean,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = SizeMedium, vertical = SizeExtraSmall)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onBackground
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.width(SizeMedium))
        RadioButton(selected = isSelected, onClick = onClick)
    }
}