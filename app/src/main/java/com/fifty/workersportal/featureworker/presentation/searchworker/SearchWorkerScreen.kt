package com.fifty.workersportal.featureworker.presentation.searchworker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.StandardBottomSheet
import com.fifty.workersportal.core.presentation.component.StandardTextField
import com.fifty.workersportal.core.presentation.ui.theme.MediumButtonHeight
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall
import com.fifty.workersportal.core.util.Screen
import com.fifty.workersportal.featureworker.presentation.component.SearchFilterChip
import com.fifty.workersportal.featureworker.presentation.component.WorkerItem
import com.fifty.workersportal.featureworker.presentation.component.WorkerListItem
import kotlinx.coroutines.launch
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchWorkerScreen(
    onNavigate: (String) -> Unit,
    onNavigateUp: () -> Unit,
    imageLoader: ImageLoader,
    viewModel: SearchWorkerViewModel = hiltViewModel()
) {
    val pagingState = viewModel.pagingState.value
    val workerSortBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showSortBottomSheet by remember { mutableStateOf(false) }
    val sortState = viewModel.sortState.value
    val filterState = viewModel.filterState.value
    val coroutineScope = rememberCoroutineScope()

    Column(Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .padding(
                    top = SizeMedium,
                    bottom = SizeMedium,
                    end = SizeMedium
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = { onNavigateUp() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back_),
                    contentDescription = stringResource(id = R.string.go_to_previous_screen),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            StandardTextField(
                basicTextFieldModifier = Modifier
                    .fillMaxWidth()
                    .height(MediumButtonHeight),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                trailingIcon = null,
                hint = stringResource(R.string.search_aryan),
                value = viewModel.searchFieldState.value.text,
                onValueChange = {
                    viewModel.onEvent(SearchWorkerEvent.Query(it))
                }
            )
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(SizeSmall),
            contentPadding = PaddingValues(horizontal = SizeMedium)
        ) {
            item {
                SearchFilterChip(
                    leadingIcon = painterResource(id = R.drawable.ic_filter),
                    trailingIcon = painterResource(id = R.drawable.ic_drop_down),
                    isSelected = (sortState.isRatingHighToLow || sortState.isDistanceLowToHigh ||
                            sortState.isWageLowToHigh || sortState.isWageHighToLow),
                    text = if (sortState.isWageHighToLow || sortState.isWageLowToHigh) {
                        stringResource(R.string.wage)
                    } else if (sortState.isRatingHighToLow) {
                        stringResource(R.string.rating)
                    } else if (sortState.isDistanceLowToHigh) {
                        stringResource(R.string.distance)
                    } else {
                        stringResource(id = R.string.sort)
                    },
                    onClick = {
                        showSortBottomSheet = true
                    }
                )
            }
            item {
                SearchFilterChip(
                    isSelected = sortState.isDistanceLowToHigh,
                    text = "Nearest",
                    onClick = {
                        viewModel.onEvent(SearchWorkerEvent.ToggleNearestSort)
                    }
                )
            }
            item {
                SearchFilterChip(
                    isSelected = filterState.isRatingFourPlus,
                    text = "Rating 4+",
                    onClick = {
                        viewModel.onEvent(SearchWorkerEvent.ToggleRatingFourPlusFilter)
                    }
                )
            }
            item {
                SearchFilterChip(
                    isSelected = filterState.isPreviouslyHired,
                    text = "Previously Hired",
                    onClick = {
                        viewModel.onEvent(SearchWorkerEvent.TogglePreviouslyHiredFilter)
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(SizeMedium))
        LazyColumn(
            contentPadding = PaddingValues(horizontal = SizeMedium),
            verticalArrangement = Arrangement.spacedBy(SizeMedium)
        ) {
            items(pagingState.items.size) { index ->
                val worker = pagingState.items[index]
                if (index >= pagingState.items.size - 1 &&
                    !pagingState.endReached &&
                    !pagingState.isLoading
                ) {
                    viewModel.loadNextWorkers()
                }
                WorkerItem(
                    worker = worker,
                    imageLoader = imageLoader,
                    onFavouriteClick = {
                        viewModel.onEvent(SearchWorkerEvent.AddToFavourite(worker.workerId))
                    },
                    onClick = {
                        onNavigate(Screen.WorkerProfileScreen.route + "?userId=${worker.workerId}")
                    }
                )
            }
        }
    }

    if (showSortBottomSheet) {
        StandardBottomSheet(
            sheetState = workerSortBottomSheetState,
            onDismiss = {
                viewModel.onEvent(SearchWorkerEvent.OnSheetDismiss)
                showSortBottomSheet = false
            }
        ) {
            SortWorkersBottomSheetContent(
                viewModel = viewModel,
                onClearAllClick = {
                    coroutineScope.launch {
                        viewModel.onEvent(SearchWorkerEvent.ClearAllSortAndFilters)
                        workerSortBottomSheetState.hide()
                        showSortBottomSheet = false
                    }
                },
                onApplyClick = {
                    coroutineScope.launch {
                        viewModel.onEvent(SearchWorkerEvent.ApplySort)
                        workerSortBottomSheetState.hide()
                        showSortBottomSheet = false
                    }
                }
            )
        }
    }
}

