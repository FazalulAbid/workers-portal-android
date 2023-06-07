package com.fifty.workersportal.featureworker.presentation.registerasworker

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.StandardAppBar
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.presentation.util.asString
import com.fifty.workersportal.core.presentation.util.makeToast
import com.fifty.workersportal.featureworker.presentation.component.RegisterPagerNavItem
import com.fifty.workersportal.featureworker.util.WorkerError
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun RegisterAsWorkerScreen(
    onNavigateUp: () -> Unit = {},
    viewModel: RegisterAsWorkerViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val firstNameFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }
    val bioFocusRequester = remember { FocusRequester() }
    val ageFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.MakeToast -> {
                    makeToast(event.uiText.asString(context), context)
                }

                else -> Unit
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.errorFlow.collectLatest { error ->
            when (error) {
                WorkerError.FieldEmpty -> {
                    pagerState.animateScrollToPage(0)
                    makeToast(R.string.error_this_field_cant_be_empty, context)
                }

                WorkerError.InputTooShort -> {
                    pagerState.animateScrollToPage(0)
                    makeToast(R.string.error_input_too_short, context)
                }

                WorkerError.InvalidBio -> {
                    pagerState.animateScrollToPage(0)
                    makeToast(R.string.enter_a_valid_bio, context)
                    bioFocusRequester.requestFocus()
                }

                WorkerError.InvalidAge -> {
                    pagerState.animateScrollToPage(0)
                    makeToast(R.string.enter_a_valid_age, context)
                    ageFocusRequester.requestFocus()
                }

                WorkerError.InvalidEmail -> {
                    pagerState.animateScrollToPage(0)
                    makeToast(R.string.enter_a_valid_email, context)
                    emailFocusRequester.requestFocus()
                }

                WorkerError.InvalidFirstName -> {
                    pagerState.animateScrollToPage(0)
                    makeToast(R.string.enter_a_valid_first_name, context)
                    firstNameFocusRequester.requestFocus()
                }

                WorkerError.NoSkillSelected -> {
                    makeToast(R.string.select_at_least_one_skill, context)
                    keyboardController?.hide()
                    pagerState.animateScrollToPage(1)
                }

                WorkerError.SkillWageError -> {
                    keyboardController?.hide()
                    pagerState.animateScrollToPage(2)
                }
            }
        }
    }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StandardAppBar(
            onNavigateUp = onNavigateUp,
            showBackArrow = true,
            title = {
                Text(
                    text = stringResource(R.string.register_as_worker),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            navActions = {
                IconButton(onClick = {
                    viewModel.onEvent(RegisterAsWorkerEvent.UpdateWorker)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_done),
                        contentDescription = stringResource(R.string.save_changes),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )
        val pages = listOf(
            stringResource(id = R.string.personal),
            stringResource(id = R.string.skills),
            stringResource(id = R.string.wages),
        )
        HorizontalPager(
            modifier = Modifier.weight(1f),
            pageCount = pages.size,
            userScrollEnabled = false,
            state = pagerState
        ) { page ->
            when (page) {
                0 -> {
                    PersonalDetailsSection(
                        viewModel = viewModel,
                        firstNameFocusRequester = firstNameFocusRequester,
                        emailFocusRequester = emailFocusRequester,
                        bioFocusRequester = bioFocusRequester,
                        ageFocusRequester = ageFocusRequester,
                        focusManager = focusManager,
                        keyboardController = keyboardController
                    )
                }

                1 -> {
                    SelectSkillsSection(viewModel = viewModel)
                }

                2 -> {
                    SkillWagesSection(
                        viewModel = viewModel,
                        focusManager = focusManager
                    )
                }
            }
        }
        Row(modifier = Modifier.padding(SizeMedium)) {
            repeat(pages.size) { index ->
                RegisterPagerNavItem(
                    text = pages[index],
                    selected = pagerState.currentPage == index
                ) {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            }
        }
    }
}