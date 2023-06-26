package com.fifty.workersportal.featureworker.presentation.registerasworker

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import coil.ImageLoader
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.StandardAppBar
import com.fifty.workersportal.core.presentation.component.StandardBottomSheet
import com.fifty.workersportal.core.presentation.component.SuccessDialogContent
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.util.AnimatedTransitionDialogEntryOnly
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.presentation.util.asString
import com.fifty.workersportal.core.presentation.util.makeToast
import com.fifty.workersportal.featureworker.presentation.component.RegisterPagerNavItem
import com.fifty.workersportal.featureworker.util.ProfileError
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.state.StateDialog
import com.maxkeppeler.sheets.state.models.ProgressIndicator
import com.maxkeppeler.sheets.state.models.State
import com.maxkeppeler.sheets.state.models.StateConfig
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun RegisterAsWorkerScreen(
    onNavigateUp: () -> Unit = {},
    imageLoader: ImageLoader,
    previousBackStackEntry: NavBackStackEntry?,
    viewModel: RegisterAsWorkerViewModel = hiltViewModel()
) {
    val state = viewModel.updateWorkerState.value
    var showSheet by remember { mutableStateOf(false) }
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

                UiEvent.WorkerProfileUpdated -> {
                    previousBackStackEntry?.savedStateHandle?.set("isWorkerProfileUpdated", true)
                    onNavigateUp()
                }

                is RegisterAsWorkerUiEvent.SelectedCategoryCount -> {
                    if (event.count < 2) {
                        viewModel.onEvent(RegisterAsWorkerEvent.UpdateWorker)
                    } else {
                        showSheet = true
                    }
                }

                else -> Unit
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.onUpdate.collectLatest {
            showSheet = false
            viewModel.isRegisterCompleteDialogDisplayed.postValue(true)
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.errorFlow.collectLatest { error ->
            when (error) {
                ProfileError.FieldEmpty -> {
                    pagerState.animateScrollToPage(0)
                    makeToast(R.string.error_this_field_cant_be_empty, context)
                }

                ProfileError.InputTooShort -> {
                    pagerState.animateScrollToPage(0)
                    makeToast(R.string.error_input_too_short, context)
                }

                ProfileError.InvalidBio -> {
                    pagerState.animateScrollToPage(0)
                    makeToast(R.string.enter_a_valid_bio, context)
                    bioFocusRequester.requestFocus()
                }

                ProfileError.InvalidAge -> {
                    pagerState.animateScrollToPage(0)
                    makeToast(R.string.enter_a_valid_age, context)
                    ageFocusRequester.requestFocus()
                }

                ProfileError.InvalidEmail -> {
                    pagerState.animateScrollToPage(0)
                    makeToast(R.string.enter_a_valid_email, context)
                    emailFocusRequester.requestFocus()
                }

                ProfileError.InvalidFirstName -> {
                    pagerState.animateScrollToPage(0)
                    makeToast(R.string.enter_a_valid_first_name, context)
                    firstNameFocusRequester.requestFocus()
                }

                ProfileError.NoSkillSelected -> {
                    makeToast(R.string.select_at_least_one_skill, context)
                    keyboardController?.hide()
                    pagerState.animateScrollToPage(2)
                }

                ProfileError.SkillWageError -> {
                    keyboardController?.hide()
                    pagerState.animateScrollToPage(3)
                }

                ProfileError.NoPrimarySkillSelected -> {
                    makeToast(R.string.select_your_primary_skill, context)
                    showSheet = true
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
                    viewModel.onEvent(RegisterAsWorkerEvent.OnSaveCheckClick)
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
            stringResource(id = R.string.identity),
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
                        imageLoader = imageLoader,
                        firstNameFocusRequester = firstNameFocusRequester,
                        emailFocusRequester = emailFocusRequester,
                        bioFocusRequester = bioFocusRequester,
                        ageFocusRequester = ageFocusRequester,
                        focusManager = focusManager,
                        keyboardController = keyboardController
                    )
                }

                1 -> {
                    IdentitySection(
                        viewModel = viewModel,
                        imageLoader = imageLoader
                    )
                }

                2 -> {
                    SelectSkillsSection(viewModel = viewModel)
                }

                3 -> {
                    SkillWagesSection(
                        viewModel = viewModel,
                        focusManager = focusManager
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = SizeMedium),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
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

    if (showSheet) {
        StandardBottomSheet(
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            onDismiss = {
                showSheet = false
            }
        ) {
            RegisterAsWorkerBottomSheetContent(
                chosenSkills = viewModel.skillsState.value.selectedSkills,
                primarySkillSelectedId = viewModel.primaryCategoryId.value,
                setSelected = {
                    viewModel.onEvent(RegisterAsWorkerEvent.PrimarySkillSelected(it))
                },
                onSaveChanges = {
                    coroutineScope.launch {
                        viewModel.onEvent(RegisterAsWorkerEvent.UpdateWorker)
                    }
                }
            )
        }
    }

    val isRegisterCompleteDialogDisplayed by viewModel
        .isRegisterCompleteDialogDisplayed
        .observeAsState(false)

    if (isRegisterCompleteDialogDisplayed) {
        AnimatedTransitionDialogEntryOnly(
            onDismissRequest = {
                viewModel.isRegisterCompleteDialogDisplayed.postValue(false)
            }
        ) {
            SuccessDialogContent(
                painter = painterResource(id = R.drawable.plumber_profile),
                title = stringResource(R.string.thank_you_for_registering_as_a_worker),
                description = stringResource(R.string.your_registration_has_been_successfully_submitted),
                buttonText = stringResource(R.string.go_to_dashboard),
                onButtonClick = {
                    viewModel.isRegisterCompleteDialogDisplayed.postValue(false)
                    previousBackStackEntry?.savedStateHandle?.set("isWorkerProfileUpdated", true)
                    onNavigateUp()
                }
            )
        }
    }

    val fetchingDataLoadingState = State.Loading(
        stringResource(id = state.loadingText ?: R.string.loading),
        ProgressIndicator.Circular()
    )
    if (state.isLoading) {
        StateDialog(
            properties = DialogProperties(
                dismissOnClickOutside = false,
                dismissOnBackPress = false
            ),
            state = rememberUseCaseState(visible = true),
            config = StateConfig(state = fetchingDataLoadingState)
        )
    }
}