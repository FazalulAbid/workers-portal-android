package com.fifty.fixitnow.featureuser.presentation.edituserprofile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.component.LargeDisplayProfilePicture
import com.fifty.fixitnow.core.presentation.component.PrimaryButton
import com.fifty.fixitnow.core.presentation.component.SecondaryHeader
import com.fifty.fixitnow.core.presentation.component.StandardAppBar
import com.fifty.fixitnow.core.presentation.component.StandardDropDownMenu
import com.fifty.fixitnow.core.presentation.component.StandardTextField
import com.fifty.fixitnow.core.presentation.ui.theme.MediumButtonHeight
import com.fifty.fixitnow.core.presentation.ui.theme.SizeLarge
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.presentation.util.CropActivityResultContract
import com.fifty.fixitnow.core.presentation.util.ToastExt
import com.fifty.fixitnow.core.presentation.util.UiEvent
import com.fifty.fixitnow.core.util.Constants
import com.fifty.fixitnow.featureworker.util.ProfileError
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.state.StateDialog
import com.maxkeppeler.sheets.state.models.ProgressIndicator
import com.maxkeppeler.sheets.state.models.State
import com.maxkeppeler.sheets.state.models.StateConfig
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalCoilApi::class,
    ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class
)
@Composable
fun UpdateUserProfileScreen(
    onNavigateUp: () -> Unit,
    previousBackStackEntry: NavBackStackEntry?,
    viewModel: UpdateUserProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.updateUserProfileState.value
    val firstNameFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }
    val ageFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var genderDropDownExpanded by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val bringIntoViewRequester = remember {
        BringIntoViewRequester()
    }

    val cropProfilePictureLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(1f, 1f)
    ) {
        viewModel.onEvent(UpdateUserProfileEvent.CropProfilePicture(it))
    }
    val profilePictureGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        if (it == null) return@rememberLauncherForActivityResult
        cropProfilePictureLauncher.launch(it)
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                UiEvent.NavigateUp -> {
                    previousBackStackEntry?.savedStateHandle?.set("isUserProfileUpdated", true)
                    onNavigateUp()
                }

                else -> Unit
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.errorFlow.collectLatest { error ->
            when (error) {
                ProfileError.FieldEmpty -> {
                    ToastExt.makeText(
                        context = context,
                        message = R.string.error_this_field_cant_be_empty
                    )
                }

                ProfileError.InputTooShort -> {
                    ToastExt.makeText(
                        context = context,
                        message = R.string.error_input_too_short
                    )
                }

                ProfileError.InvalidAge -> {
                    ToastExt.makeText(
                        context = context,
                        message = R.string.enter_a_valid_age
                    )
                    ageFocusRequester.requestFocus()
                }

                ProfileError.InvalidEmail -> {
                    ToastExt.makeText(
                        context = context,
                        message = R.string.enter_a_valid_email
                    )
                    emailFocusRequester.requestFocus()
                }

                ProfileError.InvalidFirstName -> {
                    ToastExt.makeText(
                        context = context,
                        message = R.string.enter_a_valid_first_name
                    )
                    firstNameFocusRequester.requestFocus()
                }

                else -> Unit
            }
        }
    }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        StandardAppBar(
            onNavigateUp = onNavigateUp,
            showBackArrow = true,
            title = {
                Text(
                    text = stringResource(R.string.edit_user_profile),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            LazyColumn {
                item {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = SizeMedium)
                    ) {
                        Spacer(modifier = Modifier.height(SizeLarge))
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            LargeDisplayProfilePicture(
                                painter = rememberImagePainter(
                                    data = viewModel.profileImageUri.value
                                        ?: viewModel.updateUserProfileState.value.userProfile?.profilePicture
                                ),
                                onClickEdit = {
                                    profilePictureGalleryLauncher.launch("image/*")
                                }
                            )
                        }
                        Spacer(modifier = Modifier.height(SizeLarge))
                        SecondaryHeader(
                            modifier = Modifier.padding(vertical = SizeMedium),
                            text = stringResource(R.string.enter_your_personal_info),
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.width(SizeMedium))
                        StandardTextField(
                            basicTextFieldModifier = Modifier
                                .height(MediumButtonHeight)
                                .fillMaxWidth()
                                .onFocusEvent { event ->
                                    if (event.isFocused) {
                                        coroutineScope.launch {
                                            bringIntoViewRequester.bringIntoView()
                                        }
                                    }
                                },
                            hint = stringResource(R.string.first_name),
                            value = viewModel.firstNameState.value.text,
                            onValueChange = {
                                viewModel.onEvent(
                                    UpdateUserProfileEvent.EnterFirstName(it)
                                )
                            },
                            maxLength = Constants.MAXIMUM_NAME_LENGTH,
                            titleHint = true,
                            focusRequester = firstNameFocusRequester,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Words
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Next) }
                            )
                        )
                        Spacer(modifier = Modifier.height(SizeMedium))
                        StandardTextField(
                            basicTextFieldModifier = Modifier
                                .height(MediumButtonHeight)
                                .fillMaxWidth()
                                .onFocusEvent { event ->
                                    if (event.isFocused) {
                                        coroutineScope.launch {
                                            bringIntoViewRequester.bringIntoView()
                                        }
                                    }
                                },
                            hint = stringResource(R.string.last_name),
                            maxLength = Constants.MAXIMUM_NAME_LENGTH,
                            value = viewModel.lastNameState.value.text,
                            onValueChange = {
                                viewModel.onEvent(
                                    UpdateUserProfileEvent.EnterLastName(it)
                                )
                            },
                            titleHint = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Words
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Next) }
                            )
                        )
                        Spacer(modifier = Modifier.height(SizeMedium))
                        StandardTextField(
                            basicTextFieldModifier = Modifier
                                .height(MediumButtonHeight)
                                .fillMaxWidth()
                                .onFocusEvent { event ->
                                    if (event.isFocused) {
                                        coroutineScope.launch {
                                            bringIntoViewRequester.bringIntoView()
                                        }
                                    }
                                },
                            hint = stringResource(R.string.email),
                            value = viewModel.emailState.value.text,
                            onValueChange = {
                                viewModel.onEvent(
                                    UpdateUserProfileEvent.EnterEmail(it)
                                )
                            },
                            titleHint = true,
                            focusRequester = emailFocusRequester,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Next) }
                            )
                        )
                        Spacer(modifier = Modifier.height(SizeMedium))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Box(modifier = Modifier.weight(1f)) {
                                StandardDropDownMenu(
                                    modifier = Modifier.height(MediumButtonHeight),
                                    options = Constants.genderOptions,
                                    hint = stringResource(id = R.string.gender),
                                    isTitleHintNeeded = true,
                                    expandedState = genderDropDownExpanded,
                                    onExpandedChange = { genderDropDownExpanded = it },
                                    selectedOption = viewModel.genderState.value.ifBlank { Constants.genderOptions.first() },
                                    onOptionSelected = {
                                        viewModel.onEvent(
                                            UpdateUserProfileEvent.GenderSelectedChanged(it)
                                        )
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.width(SizeMedium))
                            Box(modifier = Modifier.weight(1f)) {
                                StandardTextField(
                                    basicTextFieldModifier = Modifier
                                        .height(MediumButtonHeight)
                                        .fillMaxWidth()
                                        .onFocusEvent { event ->
                                            if (event.isFocused) {
                                                coroutineScope.launch {
                                                    bringIntoViewRequester.bringIntoView()
                                                }
                                            }
                                        },
                                    hint = stringResource(R.string.age),
                                    focusRequester = ageFocusRequester,
                                    value = viewModel.ageState.value.text,
                                    onValueChange = {
                                        viewModel.onEvent(
                                            UpdateUserProfileEvent.EnterAge(it)
                                        )
                                    },
                                    titleHint = true,
                                    maxLength = 2,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number,
                                        imeAction = ImeAction.Done
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onDone = { keyboardController?.hide() }
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .padding(SizeMedium)
                .bringIntoViewRequester(bringIntoViewRequester)
        ) {
            PrimaryButton(
                text = stringResource(id = R.string.save_changes)
            ) {
                viewModel.onEvent(UpdateUserProfileEvent.UpdateUserProfile)
            }
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