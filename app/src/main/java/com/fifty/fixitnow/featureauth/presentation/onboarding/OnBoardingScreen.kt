package com.fifty.fixitnow.featureauth.presentation.onboarding

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.ui.theme.LargeProfilePictureHeight
import com.fifty.fixitnow.core.presentation.ui.theme.SizeExtraLarge
import com.fifty.fixitnow.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.presentation.ui.theme.SizeSmall
import com.fifty.fixitnow.core.presentation.util.UiEvent
import com.fifty.fixitnow.core.util.Screen
import com.fifty.fixitnow.featureauth.presentation.util.OnBoardingPageData
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoardingScreen(
    onNavigate: (String) -> Unit,
    onNavigateWithPopBackStack: (String) -> Unit,
    imageLoader: ImageLoader,
    viewModel: OnBoardingViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val pages = listOf(
        OnBoardingPageData.First,
        OnBoardingPageData.Second,
        OnBoardingPageData.Third,
        OnBoardingPageData.Fourth
    )
    var pagerState = rememberPagerState(0)

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                UiEvent.OnBoardingScreenComplete -> {
                    onNavigateWithPopBackStack(Screen.AuthScreen.route)
                }

                else -> Unit
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HorizontalPager(
            modifier = Modifier.weight(1f),
            state = pagerState,
            verticalAlignment = Alignment.Top,
            count = pages.size
        ) { position ->
            OnBoardingPagerScreen(pages[position], imageLoader)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SizeMedium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(
                            pagerState.pageCount - 1,
                            animationSpec = tween(1000),
                            skipPages = false
                        )
                    }
                }
            ) {
                Text(
                    text = stringResource(R.string.skip),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
            Spacer(modifier = Modifier.width(SizeMedium))
            HorizontalPagerIndicator(
                pagerState = pagerState,
                indicatorShape = RoundedCornerShape(SizeExtraSmall),
                activeColor = MaterialTheme.colorScheme.primary,
                inactiveColor = MaterialTheme.colorScheme.surface
            )
            Spacer(modifier = Modifier.width(SizeMedium))
            Button(
                onClick = {
                    coroutineScope.launch {
                        if (pagerState.currentPage + 1 < pagerState.pageCount) {
                            pagerState.animateScrollToPage(
                                pagerState.currentPage + 1,
                                animationSpec = tween(1000),
                                skipPages = false
                            )
                        } else {
                            viewModel.onEvent(OnBoardingEvent.OnBoardingScreenComplete(true))
                        }
                    }
                },
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
                    .size(LargeProfilePictureHeight)
            ) {
                Icon(
                    modifier = Modifier.size(SizeExtraLarge),
                    painter = painterResource(id = R.drawable.ic_alt_arrow_forward),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun OnBoardingPagerScreen(
    onBoardingPageData: OnBoardingPageData,
    imageLoader: ImageLoader
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(SizeMedium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(MaterialTheme.shapes.medium),
            painter = rememberImagePainter(
                data = onBoardingPageData.image,
                imageLoader = imageLoader
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .padding(SizeMedium)
                .fillMaxWidth(0.9f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = onBoardingPageData.title,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(SizeSmall))
            Text(
                text = onBoardingPageData.description,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}