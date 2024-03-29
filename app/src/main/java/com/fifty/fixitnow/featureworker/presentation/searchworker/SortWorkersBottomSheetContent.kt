package com.fifty.fixitnow.featureworker.presentation.searchworker

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
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.component.HorizontalDivider
import com.fifty.fixitnow.core.presentation.component.PrimaryButton
import com.fifty.fixitnow.core.presentation.component.RadioButtonWithText
import com.fifty.fixitnow.core.presentation.component.SecondaryHeader
import com.fifty.fixitnow.core.presentation.ui.theme.LargeButtonHeight
import com.fifty.fixitnow.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium

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
        RadioButtonWithText(
            isSelected = (!sortState.isRatingHighToLow && !sortState.isWageHighToLow &&
                    !sortState.isDistanceLowToHigh && !sortState.isWageLowToHigh),
            text = "Relevance",
            onClick = { viewModel.onEvent(SearchWorkerEvent.SelectRelevance) }
        )
        RadioButtonWithText(
            isSelected = sortState.isRatingHighToLow,
            text = "Rating: High to Low",
            onClick = { viewModel.onEvent(SearchWorkerEvent.SelectRatingHighToLowSort) }
        )
        RadioButtonWithText(
            isSelected = sortState.isDistanceLowToHigh,
            text = "Distance: Low to High",
            onClick = { viewModel.onEvent(SearchWorkerEvent.SelectDistanceLowToHighSort) }
        )
        RadioButtonWithText(
            isSelected = sortState.isWageLowToHigh,
            text = "Wage: Low to High",
            onClick = { viewModel.onEvent(SearchWorkerEvent.SelectCostLowToHighSort) }
        )
        RadioButtonWithText(
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