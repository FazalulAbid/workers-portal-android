package com.fifty.fixitnow.featureworker.presentation.selectworkercategory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.component.StandardAppBar
import com.fifty.fixitnow.core.presentation.component.StandardTextField
import com.fifty.fixitnow.core.presentation.ui.theme.LargeProfilePictureHeight
import com.fifty.fixitnow.core.presentation.ui.theme.MediumButtonHeight
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.util.Screen
import com.fifty.fixitnow.core.util.toMillis
import com.fifty.fixitnow.featureworker.presentation.component.CategoryItem
import com.fifty.fixitnow.featureworkproposal.presentation.workproposal.WorkProposalEvent
import com.fifty.fixitnow.featureworkproposal.presentation.workproposal.WorkProposalViewModel
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectWorkerCategoryScreen(
    imageLoader: ImageLoader,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    searchCategoryViewModel: SearchCategoryViewModel = hiltViewModel(),
    workProposalViewModel: WorkProposalViewModel
) {
    val calenderState = rememberUseCaseState()
    val state = searchCategoryViewModel.searchState.value
    val pagingState = searchCategoryViewModel.pagingState

    Column(
        Modifier.fillMaxSize()
    ) {
        StandardAppBar(
            onNavigateUp = onNavigateUp,
            showBackArrow = true,
            title = {
                Text(
                    text = stringResource(R.string.worker_categories),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        )
        Box(
            modifier = Modifier.padding(
                top = 0.dp,
                bottom = SizeMedium,
                end = SizeMedium,
                start = SizeMedium
            )
        ) {
            StandardTextField(
                basicTextFieldModifier = Modifier
                    .fillMaxWidth()
                    .height(MediumButtonHeight),
                enabled = !state.isLoading,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = stringResource(R.string.search_for_worker_category),
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                trailingIcon = null,
                hint = stringResource(R.string.search_plumber),
                value = searchCategoryViewModel.searchFieldState.value.text,
                onValueChange = {
                    searchCategoryViewModel.onEvent(SearchCategoryEvent.Query(it))
                }
            )
        }

        LazyVerticalGrid(
            horizontalArrangement = Arrangement.spacedBy(SizeMedium),
            verticalArrangement = Arrangement.spacedBy(SizeMedium),
            contentPadding = PaddingValues(horizontal = SizeMedium),
            columns = GridCells.Fixed(count = 2)
        ) {
            items(pagingState.items.size) { index ->
                val category = pagingState.items[index]
                if (index >= pagingState.items.size - 1 &&
                    !pagingState.endReached &&
                    !pagingState.isLoading
                ) {
                    searchCategoryViewModel.loadNextWorkers()
                }
                CategoryItem(
                    category = category,
                    imageLoader = imageLoader,
                    onClick = {
                        searchCategoryViewModel.onEvent(
                            SearchCategoryEvent.SelectCategory(category)
                        )
                        calenderState.show()
                    }
                )
            }
        }
    }

    val rangeStartDate = LocalDate.now().plusDays(1)
    val rangeEndYearOffset = 1L
    val rangeEndDate = LocalDate.now().plusYears(rangeEndYearOffset)
        .withMonth(1)
        .withDayOfMonth(15)
    val range = rangeStartDate..rangeEndDate

    CalendarDialog(
        state = calenderState,
        config = CalendarConfig(
            monthSelection = true,
            boundary = range
        ),
        selection = CalendarSelection.Date { date ->
            workProposalViewModel.onEvent(WorkProposalEvent.InputProposalDate(date))
            onNavigate(
                Screen.SearchWorkerScreen.route + "?categoryId=${searchCategoryViewModel.selectedCategoryState.value?.id}?availabilityDate=${
                    date.toMillis()
                }"
            )
        }
    )
}