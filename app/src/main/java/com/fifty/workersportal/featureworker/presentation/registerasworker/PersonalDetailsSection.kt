package com.fifty.workersportal.featureworker.presentation.registerasworker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.times
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.SecondaryHeader
import com.fifty.workersportal.core.presentation.component.StandardDropDownMenu
import com.fifty.workersportal.core.presentation.component.StandardTextField
import com.fifty.workersportal.core.presentation.ui.theme.MediumButtonHeight
import com.fifty.workersportal.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall
import com.fifty.workersportal.core.util.Constants
import com.fifty.workersportal.core.util.Constants.genderOptions
import com.fifty.workersportal.featureworker.presentation.component.OpenToWorkSwitch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PersonalDetailsSection(
    modifier: Modifier = Modifier,
    viewModel: RegisterAsWorkerViewModel,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    keyboardController: SoftwareKeyboardController?
) {

    var genderDropDownExpanded by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        item {
            Column(
                modifier = Modifier
                    .padding(horizontal = SizeMedium)
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(SizeExtraSmall))
                OpenToWorkSwitch(
                    modifier = Modifier.fillMaxWidth(),
                    checked = viewModel.openToWorkState.value,
                    onCheckedChange = {
                        viewModel.onEvent(RegisterAsWorkerEvent.ToggleOpenToWork)
                    }
                )
                Spacer(modifier = Modifier.height(SizeExtraSmall))
                SecondaryHeader(
                    text = stringResource(R.string.enter_your_personal_info),
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        StandardTextField(
                            basicTextFieldModifier = Modifier
                                .height(MediumButtonHeight)
                                .fillMaxWidth(),
                            hint = stringResource(R.string.first_name),
                            value = viewModel.firstNameState.value.text,
                            onValueChange = {
                                viewModel.onEvent(
                                    RegisterAsWorkerEvent.EnterFirstName(it)
                                )
                            },
                            maxLength = Constants.MAXIMUM_NAME_LENGTH,
                            titleHint = true,
                            focusRequester = focusRequester,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Words
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Next) }
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(SizeMedium))
                    Box(modifier = Modifier.weight(1f)) {
                        StandardTextField(
                            basicTextFieldModifier = Modifier
                                .height(MediumButtonHeight)
                                .fillMaxWidth(),
                            hint = stringResource(R.string.last_name),
                            maxLength = Constants.MAXIMUM_NAME_LENGTH,
                            value = viewModel.lastNameState.value.text,
                            onValueChange = {
                                viewModel.onEvent(
                                    RegisterAsWorkerEvent.EnterLastName(it)
                                )
                            },
                            titleHint = true,
                            focusRequester = focusRequester,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Words
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Next) }
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(SizeMedium))
                StandardTextField(
                    basicTextFieldModifier = Modifier
                        .height(MediumButtonHeight)
                        .fillMaxWidth(),
                    hint = stringResource(R.string.email),
                    value = viewModel.emailState.value.text,
                    onValueChange = {
                        viewModel.onEvent(
                            RegisterAsWorkerEvent.EnterEmail(it)
                        )
                    },
                    titleHint = true,
                    focusRequester = focusRequester,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Next) }
                    )
                )
                Spacer(modifier = Modifier.height(SizeMedium))
                StandardTextField(
                    basicTextFieldModifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = MediumButtonHeight, max = (3f * MediumButtonHeight))
                        .padding(vertical = SizeSmall),
                    hint = stringResource(R.string.bio),
                    focusRequester = focusRequester,
                    value = viewModel.bioState.value.text,
                    onValueChange = {
                        viewModel.onEvent(
                            RegisterAsWorkerEvent.EnterBio(it)
                        )
                    },
                    titleHint = true,
                    singleLine = false,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusRequester.requestFocus() }
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
                            options = genderOptions,
                            hint = stringResource(id = R.string.gender),
                            isTitleHintNeeded = true,
                            expandedState = genderDropDownExpanded,
                            onExpandedChange = { genderDropDownExpanded = it },
                            selectedOption = viewModel.genderState.value,
                            onOptionSelected = {
                                viewModel.onEvent(
                                    RegisterAsWorkerEvent.GenderSelectedChanged(it)
                                )
                            }
                        )
                    }
                    Spacer(modifier = Modifier.width(SizeMedium))
                    Box(modifier = Modifier.weight(1f)) {
                        StandardTextField(
                            basicTextFieldModifier = Modifier
                                .height(MediumButtonHeight)
                                .fillMaxWidth(),
                            hint = stringResource(R.string.age),
                            focusRequester = focusRequester,
                            value = viewModel.ageState.value.text,
                            onValueChange = {
                                viewModel.onEvent(
                                    RegisterAsWorkerEvent.EnterAge(it)
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