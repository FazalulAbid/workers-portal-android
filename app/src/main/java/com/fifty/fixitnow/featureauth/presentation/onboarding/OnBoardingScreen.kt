package com.fifty.fixitnow.featureauth.presentation.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.fifty.fixitnow.core.util.Screen
import com.fifty.fixitnow.featureauth.presentation.util.OnBoardingPageData
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoardingScreen(
    onNavigate: (String) -> Unit,
    popBackStack: () -> Unit,
    imageLoader: ImageLoader,
    viewModel: OnBoardingViewModel = hiltViewModel()
) {
    val pages = listOf(
        OnBoardingPageData.First,
        OnBoardingPageData.Second,
        OnBoardingPageData.Third,
        OnBoardingPageData.Fourth
    )
    var pagerState = rememberPagerState(
        pageCount = pages.size
    )

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            modifier = Modifier.weight(10f),
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { position ->
            OnBoardingPagerScreen(pages[position])
        }
        HorizontalPagerIndicator(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .weight(1f),
            pagerState = pagerState
        )
    }
}

@Composable
fun OnBoardingPagerScreen(
    onBoardingPageData: OnBoardingPageData
) {
    Column {
        Text(text = onBoardingPageData.title)
        Text(text = onBoardingPageData.description)
    }
}