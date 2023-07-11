package com.fifty.workersportal.featureworker.presentation.selectworkercategory

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import coil.ImageLoader
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.StandardAppBar
import com.fifty.workersportal.core.presentation.component.StandardTextField
import com.fifty.workersportal.core.presentation.ui.theme.LargeProfilePictureHeight
import com.fifty.workersportal.core.presentation.ui.theme.MediumButtonHeight
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.util.Screen
import com.fifty.workersportal.featureworker.presentation.component.CategoryItem
import com.fifty.workersportal.featureworkproposal.presentation.workproposal.WorkProposalEvent
import com.fifty.workersportal.featureworkproposal.presentation.workproposal.WorkProposalViewModel
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
    val search by searchCategoryViewModel.search.collectAsStateWithLifecycle()
    val state = searchCategoryViewModel.searchState.value
    val searchedCategories =
        searchCategoryViewModel.categorySearchResults.collectAsLazyPagingItems()

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
                value = search,
                onValueChange = {
                    searchCategoryViewModel.onEvent(SearchCategoryEvent.Query(it))
                }
            )
        }
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyVerticalGrid(
                horizontalArrangement = Arrangement.spacedBy(SizeMedium),
                verticalArrangement = Arrangement.spacedBy(SizeMedium),
                contentPadding = PaddingValues(horizontal = SizeMedium),
                columns = GridCells.Adaptive(minSize = LargeProfilePictureHeight)
            ) {
                items(searchedCategories.itemCount)
                { index ->
                    searchedCategories[index]?.let {
                        CategoryItem(
                            category = it,
                            imageLoader = imageLoader,
                            onClick = {
                                calenderState.show()
                            }
                        )
                    }
                }
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
            onNavigate(Screen.SearchWorkerScreen.route)
        }
    )
}