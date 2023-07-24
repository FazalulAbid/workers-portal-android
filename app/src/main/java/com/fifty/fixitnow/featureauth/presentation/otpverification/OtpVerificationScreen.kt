package com.fifty.fixitnow.featureauth.presentation.otpverification

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.component.StandardAppBar
import com.fifty.fixitnow.core.presentation.ui.theme.MediumButtonHeight
import com.fifty.fixitnow.core.presentation.ui.theme.SizeExtraLarge
import com.fifty.fixitnow.core.presentation.ui.theme.SizeLarge
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.presentation.util.UiEvent
import com.fifty.fixitnow.core.presentation.util.asString
import com.fifty.fixitnow.core.util.NavigationParent
import com.fifty.fixitnow.presentation.phoneotp.OtpTextField
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OtpVerificationScreen(
    onNavigateWithPopBackStack: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: OtpVerificationViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val otpState = viewModel.otpTextFieldState.value
    val context = LocalContext.current
    var otpResendSuccessMessage by remember { mutableStateOf("") }
    val timerValue by viewModel.timerValue.collectAsState()
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

                is UiEvent.ShowMessage -> {
                    otpResendSuccessMessage = event.uiText.asString(context)
                    delay(5000)
                    otpResendSuccessMessage = ""
                }

                is UiEvent.OnLogin -> {
                    keyboardController?.hide()
                    onNavigateWithPopBackStack(NavigationParent.Home.route)
                }

                else -> Unit
            }
        }
    }
    DisposableEffect(Unit) {
        viewModel.startResendTimer()
        onDispose {
            viewModel.stopResendTimer()
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
                    text = stringResource(R.string.otp_verification),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        )
        Spacer(modifier = Modifier.height(SizeExtraLarge))
        Text(
            text = stringResource(R.string.we_have_sent_a_verification_code_to),
            style = MaterialTheme.typography.bodyLarge.copy(
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
        )
        Spacer(modifier = Modifier.height(SizeMedium))
        Text(
            text = "${state.countryCode}-${state.phoneNumber}",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
        Spacer(modifier = Modifier.height(SizeLarge))
        OtpTextField(
            otpText = otpState.text,
            onOtpTextChange = {
                viewModel.onEvent(OtpVerificationEvent.EnterOtp(it))
            }
        )
        Spacer(modifier = Modifier.height(SizeLarge))
        Text(
            text = otpResendSuccessMessage,
            style = MaterialTheme.typography.bodyLarge.copy(
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary
            )
        )
        Spacer(modifier = Modifier.height(SizeLarge))
        OutlinedButton(
            modifier = Modifier
                .height(MediumButtonHeight)
                .widthIn(min = 200.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background,
                disabledContainerColor = MaterialTheme.colorScheme.background,
                disabledContentColor = MaterialTheme.colorScheme.onSurface,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            enabled = timerValue == 0,
            onClick = {
                viewModel.onEvent(OtpVerificationEvent.ResendOtp)
            }
        ) {
            Text(
                text = if (timerValue == 0) {
                    stringResource(id = R.string.resend_otp)
                } else {
                    stringResource(R.string.resend_otp_in_x, timerValue)
                }
            )
        }
        Spacer(modifier = Modifier.height(SizeLarge))
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            onClick = {},
        ) {
            Text(text = stringResource(R.string.try_other_login_methods))
        }
    }
}