package com.fifty.workersportal.featureworker.presentation.selectworkercategory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.StandardAppBar
import com.fifty.workersportal.core.presentation.component.StandardTextField
import com.fifty.workersportal.core.presentation.ui.theme.ExtraSmallProfilePictureHeight
import com.fifty.workersportal.core.presentation.ui.theme.LargeProfilePictureHeight
import com.fifty.workersportal.core.presentation.ui.theme.MediumButtonHeight
import com.fifty.workersportal.core.presentation.ui.theme.MediumProfilePictureHeight
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SmallProfilePictureHeight
import com.fifty.workersportal.core.util.Screen
import com.fifty.workersportal.featureworker.presentation.component.CategoryItem

@Composable
fun SelectWorkerCategoryScreen(
    imageLoader: ImageLoader,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: SearchCategoryViewModel = hiltViewModel()
) {
    val state = viewModel.searchState.value
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
                    viewModel.onEvent(SearchCategoryEvent.Query(it))
                }
            )
        }
        LazyVerticalGrid(
            horizontalArrangement = Arrangement.SpaceEvenly,
            columns = GridCells.Adaptive(minSize = 110.dp)
        ) {
            items(state.workerCategories) {
                CategoryItem(
                    category = it,
                    imageLoader = imageLoader,
                    onClick = {
                        onNavigate(Screen.WorkerListScreen.route)
                    }
                )
            }
        }
    }
}