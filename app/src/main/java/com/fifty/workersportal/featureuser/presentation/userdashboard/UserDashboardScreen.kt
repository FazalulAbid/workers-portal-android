package com.fifty.workersportal.featureuser.presentation.userdashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.DashboardNavigationAndProfile
import com.fifty.workersportal.core.presentation.component.HorizontalDivider
import com.fifty.workersportal.core.presentation.component.SecondaryHeader
import com.fifty.workersportal.core.presentation.component.StandardTextField
import com.fifty.workersportal.core.presentation.ui.theme.MediumButtonHeight
import com.fifty.workersportal.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall
import com.fifty.workersportal.featureuser.presentation.component.AutoSlidingCarousal
import com.fifty.workersportal.featureuser.presentation.component.DashboardGreetingText
import com.fifty.workersportal.featureworker.presentation.component.CategoryItem
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalPagerApi::class)
@Composable
fun UserDashboardScreen(
    onNavigate: (String) -> Unit = {},
    viewModel: UserDashboardViewModel = hiltViewModel()
) {
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        val list = listOf(
//            Screen.WorkerDashboardScreen.route,
//            Screen.SelectWorkerCategoryScreen.route,
//            Screen.WorkerListScreen.route,
//            Screen.WorkerProfileScreen.route,
//            Screen.RegisterAsWorkerScreen.route,
//            Screen.MessageScreen.route,
//        )
//        LazyHorizontalGrid(
//            rows = GridCells.Adaptive(minSize = 70.dp),
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            items(list) {
//                Button(onClick = {
//                    onNavigate(it)
//                }, Modifier.padding(SizeMedium)) {
//                    Text(text = it)
//                }
//            }
//        }
//    }
    LazyColumn {
        item {
            Column(
                modifier = Modifier.padding(SizeMedium)
            ) {
                DashboardGreetingText(
                    name = "Fazalul",
                    greetingText = stringResource(R.string.dashboard_greeting_prefix)
                )
                Spacer(Modifier.height(SizeExtraSmall))
                DashboardNavigationAndProfile()
                Spacer(modifier = Modifier.height(SizeMedium))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(SizeMedium))
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
                    value = "",
                    onValueChange = {

                    }
                )
            }
        }
        item {
            Column {
                Spacer(modifier = Modifier.height(SizeSmall))
                AutoSlidingCarousal()
                Spacer(modifier = Modifier.height(SizeMedium))
            }
        }
        item {
            Column(Modifier.padding(horizontal = SizeMedium)) {
                SecondaryHeader(
                    text = stringResource(R.string.suggested_categories),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            LazyRow() {
                items(20) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .padding(SizeMedium)
                            .background(Color.Red)
                    )
                }
            }
        }
    }
}