package com.fifty.workersportal.featureuser.presentation.edituserprofile

import android.widget.Space
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.PrimaryButton
import com.fifty.workersportal.core.presentation.component.SecondaryHeader
import com.fifty.workersportal.core.presentation.component.StandardAppBar
import com.fifty.workersportal.core.presentation.component.StandardDropDownMenu
import com.fifty.workersportal.core.presentation.component.StandardTextField
import com.fifty.workersportal.core.presentation.ui.theme.ExtraExtraLargeProfilePictureHeight
import com.fifty.workersportal.core.presentation.ui.theme.MediumButtonHeight
import com.fifty.workersportal.core.presentation.ui.theme.SizeLarge
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.util.CropActivityResultContract
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.util.Constants
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalCoilApi::class, ExperimentalComposeUiApi::class)
@Composable
fun EditUserProfileScreen(
    onNavigate: (String) -> Unit,
    onNavigateUp: () -> Unit,
    viewModel: EditUserProfileViewModel = hiltViewModel()
) {
    val firstNameFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }
    val ageFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var genderDropDownExpanded by remember { mutableStateOf(false) }

    val cropProfilePictureLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(1f, 1f)
    ) {
        viewModel.onEvent(EditUserProfileEvent.CropProfilePicture(it))
    }
    val profilePictureGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        if (it == null) return@rememberLauncherForActivityResult
        cropProfilePictureLauncher.launch(it)
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
                .padding(SizeMedium)
        ) {
            Spacer(modifier = Modifier.height(SizeLarge))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = viewModel.profileImageUri.value
                            ?: viewModel.editUserProfileState.value.userProfile?.profilePicture
                    ),
                    contentDescription = null,
                    Modifier
                        .size(ExtraExtraLargeProfilePictureHeight)
                        .clip(CircleShape)
                        .clickable {
                            profilePictureGalleryLauncher.launch("image/*")
                        },
                    contentScale = ContentScale.Crop
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
                    .fillMaxWidth(),
                hint = stringResource(R.string.first_name),
                value = viewModel.firstNameState.value.text,
                onValueChange = {
                    viewModel.onEvent(
                        EditUserProfileEvent.EnterFirstName(it)
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
                    .fillMaxWidth(),
                hint = stringResource(R.string.last_name),
                maxLength = Constants.MAXIMUM_NAME_LENGTH,
                value = viewModel.lastNameState.value.text,
                onValueChange = {
                    viewModel.onEvent(
                        EditUserProfileEvent.EnterLastName(it)
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
                    .fillMaxWidth(),
                hint = stringResource(R.string.email),
                value = viewModel.emailState.value.text,
                onValueChange = {
                    viewModel.onEvent(
                        EditUserProfileEvent.EnterEmail(it)
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
                        selectedOption = viewModel.genderState.value,
                        onOptionSelected = {
                            viewModel.onEvent(
                                EditUserProfileEvent.GenderSelectedChanged(it)
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
                        focusRequester = ageFocusRequester,
                        value = viewModel.ageState.value.text,
                        onValueChange = {
                            viewModel.onEvent(
                                EditUserProfileEvent.EnterAge(it)
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
        Box(modifier = Modifier.padding(SizeMedium)) {
            PrimaryButton(
                text = stringResource(id = R.string.save_changes)
            ) {

            }
        }
    }
}