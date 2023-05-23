package com.fifty.workersportal.featureauth.presentation.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.FullWidthButton
import com.fifty.workersportal.core.presentation.component.StandardImageButton
import com.fifty.workersportal.core.presentation.component.TextBetweenLines
import com.fifty.workersportal.core.presentation.ui.theme.SizeExtraLarge
import com.fifty.workersportal.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.workersportal.core.presentation.ui.theme.SizeLarge
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall
import com.fifty.workersportal.core.presentation.ui.theme.StrokeThickness
import com.fifty.workersportal.featureauth.presentation.component.CountryPickerField
import com.fifty.workersportal.featureauth.presentation.component.PhoneNumberTextField
import kotlin.math.roundToInt

@Composable
fun AuthScreen() {
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Banner image
            Image(
                modifier = Modifier
                    .weight(4f)
                    .clip(
                        RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomStart = SizeMedium,
                            bottomEnd = SizeMedium
                        )
                    ),
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
                    .weight(6f)
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
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    TextBetweenLines(
                        text = stringResource(R.string.login_or_sign_up)
                    )
                    Spacer(modifier = Modifier.height(SizeMedium))
                    Row {
                        CountryPickerField(
                            modifier = Modifier
                                .height(50.dp),
                            "https://flagcdn.com/w320/in.png",
                            onCountryClick = {}
                        )
                        Spacer(modifier = Modifier.width(SizeSmall))
                        PhoneNumberTextField(
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                            countryCodeText = "+91",
                            phoneNumberText = "9562520502",
                            hint = "Enter Phone Number",
                            onValueChange = {

                            },
                            onClearTextClick = {}
                        )
                    }
                    Spacer(modifier = Modifier.height(SizeMedium))
                    FullWidthButton(text = stringResource(R.string.continue_text), onClick = {})
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    TextBetweenLines(text = stringResource(R.string.or))
                    Spacer(modifier = Modifier.height(SizeMedium))
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
