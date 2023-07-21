package com.fifty.fixitnow.featurefavorites.presentation.favouriteworkers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.component.StandardAppBar
import com.fifty.fixitnow.core.presentation.ui.theme.ScaffoldBottomPaddingValue
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.presentation.util.makeToast
import com.fifty.fixitnow.core.util.Screen
import com.fifty.fixitnow.featureworker.presentation.component.WorkerItem

@Composable
fun FavoriteWorkersScreen(
    imageLoader: ImageLoader,
    onNavigate: (String) -> Unit,
    viewModel: FavouriteWorkersViewModel = hiltViewModel()
) {
    val pagingState = viewModel.pagingState
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.onEvent(FavouriteWorkersEvent.LoadFavouriteWorkers)
    }

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
                        text = stringResource(R.string.favourite_workers),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(SizeMedium),
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
                            viewModel.onEvent(FavouriteWorkersEvent.ToggleFavouriteWorkers(worker.workerId))
                        },
                        onClick = {
                            if (worker.openToWork) {
                                onNavigate(Screen.WorkerProfileScreen.route + "?userId=${worker.workerId}")
                            } else {
                                makeToast(
                                    message = "${worker.firstName} ${worker.lastName} is not currently accepting works",
                                    context = context
                                )
                            }
                        }
                    )
                }
                item {
                    if (pagingState.isLoading) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(SizeMedium),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}
