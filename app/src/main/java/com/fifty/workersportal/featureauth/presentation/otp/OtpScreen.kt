package com.fifty.workersportal.featureauth.presentation.otp

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.StandardAppBar
import com.fifty.workersportal.core.presentation.ui.theme.MediumButtonHeight
import com.fifty.workersportal.core.presentation.ui.theme.SizeExtraLarge
import com.fifty.workersportal.core.presentation.ui.theme.SizeLarge
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.util.NavArgConstants
import com.fifty.workersportal.core.presentation.util.UiEvent
import com.fifty.workersportal.core.presentation.util.asString
import com.fifty.workersportal.core.util.Screen
import com.fifty.workersportal.presentation.phoneotp.OtpTextField
import kotlinx.coroutines.flow.collectLatest

@Composable
fun OtpScreen(
    onNavigate: (String) -> Unit = {},
    viewModel: OtpViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.MakeToast -> {
                    Toast.makeText(
                        context, event.uiText.asString(context),
                        Toast.LENGTH_LONG
                    ).show()
                }

                is UiEvent.Navigate -> {

                }

                else -> Unit
            }
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StandardAppBar(
            showBackArrow = true,
            title = {
                Text(
                    text = stringResource(R.string.otp_verification),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Medium
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
            text = "+${state.countryCode}-${state.phoneNumber}",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
        Spacer(modifier = Modifier.height(SizeLarge))
        OtpTextField(onOtpTextChange = {})
        Spacer(modifier = Modifier.height(SizeLarge))
        OutlinedButton(
            modifier = Modifier.height(MediumButtonHeight),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background,
                disabledContainerColor = MaterialTheme.colorScheme.background,
                disabledContentColor = MaterialTheme.colorScheme.onSurface,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            onClick = {}
        ) {
            Text(text = stringResource(R.string.resend_otp_in_x, 12))
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