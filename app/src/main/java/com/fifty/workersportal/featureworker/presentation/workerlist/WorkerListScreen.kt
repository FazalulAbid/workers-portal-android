package com.fifty.workersportal.featureworker.presentation.workerlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.StandardAppBar
import com.fifty.workersportal.core.presentation.component.StandardSearchTextField
import com.fifty.workersportal.core.presentation.ui.theme.MediumButtonHeight
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.featureworker.presentation.component.WorkerCategoryItem
import com.fifty.workersportal.featureworker.presentation.selectworkercategory.SelectWorkerCategoryEvent
import com.fifty.workersportal.featureworker.presentation.selectworkercategory.SelectWorkerCategoryViewModel

@Composable
fun WorkerListScreen(
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: WorkerListViewModel = hiltViewModel()
) {
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
                        fontWeight = FontWeight.Medium
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        )
        Box(modifier = Modifier.padding(SizeMedium)) {
            StandardSearchTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MediumButtonHeight),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = stringResource(R.string.search_for_worker_category),
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                trailingIcon = null,
                hint = stringResource(R.string.search_plumber),
                value = viewModel.searchFieldState.value.text,
                onValueChange = {
                    viewModel.onEvent(WorkerListEvent.Query(it))
                }
            )
        }
        LazyColumn() {
            items(50) {

            }
        }
    }
}