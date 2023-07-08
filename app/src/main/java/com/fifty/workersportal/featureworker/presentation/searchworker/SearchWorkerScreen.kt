package com.fifty.workersportal.featureworker.presentation.searchworker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.StandardTextField
import com.fifty.workersportal.core.presentation.ui.theme.MediumButtonHeight
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall
import com.fifty.workersportal.featureworker.presentation.component.SearchFilterChip
import com.fifty.workersportal.featureworker.presentation.component.WorkerListItem
import kotlin.random.Random

@Composable
fun SearchWorkerScreen(
    onNavigate: (String) -> Unit,
    onNavigateUp: () -> Unit,
    viewModel: SearchWorkerViewModel = hiltViewModel()
) {
    Column {
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
            horizontalArrangement = Arrangement.spacedBy(SizeMedium),
            contentPadding = PaddingValues(horizontal = SizeMedium)
        ) {
            items(4) {
                SearchFilterChip(
                    leadingIcon = painterResource(id = R.drawable.ic_filter),
                    trailingIcon = painterResource(id = R.drawable.ic_close),
                    isSelected = Random.nextBoolean(),
                    text = "Sort",
                    onClick = {

                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(SizeMedium))
        LazyColumn {
            items(20) {
                WorkerListItem(
                    isFavourite = true,
                    lottieComposition = null
                )
            }
        }
    }
}

