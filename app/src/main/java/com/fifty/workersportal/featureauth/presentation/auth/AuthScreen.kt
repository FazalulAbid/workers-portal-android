package com.fifty.workersportal.featureauth.presentation.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.fifty.workersportal.core.presentation.component.TextBetweenLines
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
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
                modifier = Modifier
                    .weight(6f)
                    .fillMaxWidth()
                    .padding(SizeMedium)
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
                    modifier = Modifier.padding(vertical = SizeMedium),
                    text = stringResource(R.string.login_or_sign_up)
                )
//                PhoneNumberTextField(
//                    modifier = Modifier
//                        .weight(1f)
//                        .height(50.dp),
//                    text = "9562520502",
//                    hint = "Enter Phone Number",
//                    onValueChange = {
//
//                    },
//                    onClearTextClick = {}
//                )
            }
        }
    }
}