package com.fifty.workersportal.featureauth.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.PrimaryButton
import com.fifty.workersportal.core.presentation.component.Keyboard
import com.fifty.workersportal.core.presentation.component.StandardImageButton
import com.fifty.workersportal.core.presentation.component.TextBetweenLines
import com.fifty.workersportal.core.presentation.component.keyboardAsState
import com.fifty.workersportal.core.presentation.ui.theme.LargeButtonHeight
import com.fifty.workersportal.core.presentation.ui.theme.SizeLarge
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.presentation.util.asString
import com.fifty.workersportal.core.util.Constants
import com.fifty.workersportal.core.util.Screen
import com.fifty.workersportal.featureauth.presentation.component.CountryPickerField
import com.fifty.workersportal.featureauth.presentation.component.PhoneNumberTextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun AuthScreen(
    snackbarHostState: SnackbarHostState,
    currentBackStackEntry: NavBackStackEntry?,
    onNavigate: (String) -> Unit = {},
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    viewModel.countryName = currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<String>("name")?.observeAsState(Constants.DEFAULT_COUNTRY_NAME)!!

    viewModel.countryCode = currentBackStackEntry
        .savedStateHandle
        .getLiveData<String>("callingCode").observeAsState(Constants.DEFAULT_COUNTRY_CODE)

    viewModel.countryFlagUrl = currentBackStackEntry
        .savedStateHandle
        .getLiveData<String>("flagUrl").observeAsState(Constants.DEFAULT_COUNTRY_FLAG_URL)

    val phoneNumber = viewModel.phoneNumberState.value

    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val keyboardOpenState by keyboardAsState()
    val context = LocalContext.current

    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(key1 = true, key2 = keyboardController) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.MakeToast -> {
                    Toast.makeText(
                        context, event.uiText.asString(context),
                        Toast.LENGTH_LONG
                    ).show()
                }

                is UiEvent.HideKeyboard -> {
                    keyboardController?.hide()
                }

                is UiEvent.Navigate -> {
                    onNavigate(event.route)
                }

                else -> Unit
            }
        }
    }
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Banner image
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.45f),
                painter = painterResource(id = R.drawable.login_screen_banner),
                contentDescription = stringResource(R.string.login_screen_banner),
                contentScale = ContentScale.Crop
            )
            // Login content section
            val maxWidthPercent = 0.8f
            val screenWidth = LocalConfiguration.current.screenWidthDp
            val maximumWidth = (screenWidth * maxWidthPercent).roundToInt()
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .weight(0.55f)
                    .fillMaxWidth()
                    .padding(horizontal = SizeMedium, vertical = SizeLarge)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(maxWidthPercent)
                        .widthIn(maximumWidth.dp),
                    text = stringResource(R.string.kerala_s_1_workers_hiring_and_posting_app),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
                TextBetweenLines(
                    text = stringResource(R.string.login_or_sign_up)
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row {
                        CountryPickerField(
                            modifier = Modifier
                                .height(LargeButtonHeight),
                            flagImageUrl = viewModel.countryFlagUrl.value
                                ?: Constants.DEFAULT_COUNTRY_FLAG_URL,
                            onCountryClick = {
                                onNavigate(Screen.SelectCountryScreen.route)
                            }
                        )
                        Spacer(modifier = Modifier.width(SizeSmall))
                        PhoneNumberTextField(
                            modifier = Modifier
                                .weight(1f)
                                .height(LargeButtonHeight)
                                .onFocusEvent { event ->
                                    if (event.isFocused) {
                                        coroutineScope.launch {
                                            bringIntoViewRequester.bringIntoView()
                                        }
                                    }
                                },
                            countryCodeText = viewModel.countryCode.value,
                            phoneNumberText = phoneNumber.text,
                            hint = stringResource(R.string.enter_phone_number),
                            keyboardType = KeyboardType.Number,
                            onValueChange = {
                                viewModel.onEvent(AuthEvent.EnteredPhoneNumber(it))
                            },
                            onClearTextClick = {
                                viewModel.onEvent(AuthEvent.ClearPhoneNumber)
                            },
                            onDone = { focusManager.clearFocus() },
                        )
                    }
                    Spacer(modifier = Modifier.height(SizeMedium))
                    PrimaryButton(
                        modifier = Modifier.bringIntoViewRequester(bringIntoViewRequester),
                        text = stringResource(R.string.continue_text),
                        isLoading = state.isGetOtpLoading
                    ) {
                        viewModel.onEvent(AuthEvent.GetOtp)
                    }
                }
                if (keyboardOpenState == Keyboard.Closed) {
                    TextBetweenLines(text = stringResource(R.string.or))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        StandardImageButton(
                            size = 60.dp,
                            imageIcon = painterResource(id = R.drawable.ic_google),
                            onClick = {}
                        )
                        Spacer(modifier = Modifier.height(SizeMedium))
                        TermsAndConditionSection()
                    }
                }
            }
        }
    }
}

@Composable
fun TermsAndConditionSection(
    onClickTermsOfService: () -> Unit = {},
    onClickPrivacyPolicy: () -> Unit = {},
    onClickContentPolicy: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            style = MaterialTheme.typography.labelSmall,
            text = stringResource(R.string.by_continuing_you_agree_to_our)
        )
        Row {
            TermsAndConditionButton(
                buttonText = stringResource(id = R.string.terms_of_service),
                onClick = onClickTermsOfService
            )
            Spacer(modifier = Modifier.width(SizeSmall))
            TermsAndConditionButton(
                buttonText = stringResource(id = R.string.privacy_policy),
                onClick = onClickPrivacyPolicy
            )
            Spacer(modifier = Modifier.width(SizeSmall))
            TermsAndConditionButton(
                buttonText = stringResource(id = R.string.content_policy),
                onClick = onClickContentPolicy
            )
        }
    }
}

@Composable
fun TermsAndConditionButton(buttonText: String, onClick: () -> Unit) {
    Text(
        style = MaterialTheme.typography.labelSmall,
        modifier = Modifier
            .clickable { onClick() },
        text = buttonText,
    )
}
