package com.fifty.fixitnow.featurehistory.presentation.historyscreen

import android.util.Range
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.core.util.toRange
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil.ImageLoader
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.component.HorizontalDivider
import com.fifty.fixitnow.core.presentation.component.StandardAppBar
import com.fifty.fixitnow.core.presentation.component.StandardBottomSheet
import com.fifty.fixitnow.core.presentation.ui.theme.ScaffoldBottomPaddingValue
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.presentation.ui.theme.SizeSmall
import com.fifty.fixitnow.core.util.formatDateRange
import com.fifty.fixitnow.core.util.items
import com.fifty.fixitnow.featurehistory.presentation.component.WorkHistoryListItem
import com.fifty.fixitnow.featureworker.presentation.component.ReviewItem
import com.fifty.fixitnow.featureworker.presentation.searchworker.SearchWorkerEvent
import com.fifty.fixitnow.featureworker.presentation.searchworker.SortWorkersBottomSheetContent
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun WorkHistoryScreen(
    imageLoader: ImageLoader,
    viewModel: WorkHistoryViewModel = hiltViewModel()
) {
    val workHistory = viewModel.workHistory.collectAsLazyPagingItems()
    val coroutineScope = rememberCoroutineScope()
    val filterBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showFilterBottomSheet by remember { mutableStateOf(false) }
    val calenderState = rememberUseCaseState()
    val timeBoundary = LocalDate.now().let { now -> now.minusYears(2)..now.plusYears(2) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = ScaffoldBottomPaddingValue),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StandardAppBar(
                showBackArrow = false,
                title = {
                    Text(
                        text = stringResource(R.string.work_history),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SizeMedium),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val hello = viewModel.selectedRange.value
                Text(
                    text = viewModel.selectedRange.value.formatDateRange(),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Medium
                    ),
                    maxLines = 1
                )
                Spacer(modifier = Modifier.width(SizeMedium))
                Row(
                    modifier = Modifier.clickable {
                        showFilterBottomSheet = true
                    },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(SizeMedium),
                        painter = painterResource(id = R.drawable.ic_filter),
                        contentDescription = stringResource(id = R.string.filter_work_history),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(SizeSmall))
                    Text(
                        text = stringResource(R.string.filter),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(SizeSmall))
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(
                    top = SizeSmall,
                    start = SizeMedium,
                    end = SizeMedium,
                    bottom = SizeMedium
                ),
                verticalArrangement = Arrangement.spacedBy(SizeMedium)
            ) {
                items(workHistory) { workHistory ->
                    workHistory?.let {
                        WorkHistoryListItem(
                            imageLoader = imageLoader,
                            workHistory = workHistory
                        )
                    }
                }
            }
        }
    }


    if (showFilterBottomSheet) {
        StandardBottomSheet(
            sheetState = filterBottomSheetState,
            onDismiss = {
                viewModel.onEvent(WorkHistoryEvent.OnSheetDismiss)
                showFilterBottomSheet = false
            }
        ) {
            WorkHistoryFilterBottomSheetContent(
                viewModel = viewModel,
                onClearAllClick = {
                    coroutineScope.launch {
                        viewModel.onEvent(WorkHistoryEvent.OnClearFilterClick)
                        filterBottomSheetState.hide()
                        showFilterBottomSheet = false
                    }
                },
                calenderState = calenderState,
                onApplyClick = {
                    coroutineScope.launch {
                        viewModel.onEvent(WorkHistoryEvent.OnApplyFilterClick)
                        filterBottomSheetState.hide()
                        showFilterBottomSheet = false
                    }
                }
            )
        }
    }

    CalendarDialog(
        state = calenderState,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            boundary = timeBoundary,
            style = CalendarStyle.MONTH,
        ),
        selection = CalendarSelection.Period(
            selectedRange = viewModel.selectedRange.value.toRange()
        ) { startDate, endDate ->
            viewModel.onEvent(WorkHistoryEvent.ChangeSelectedRange(Range(startDate, endDate)))
        },
    )
}