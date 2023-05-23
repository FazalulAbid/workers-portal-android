package com.fifty.workersportal.featureauth.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall
import com.fifty.workersportal.core.presentation.ui.theme.MediumStrokeThickness
import com.fifty.workersportal.core.util.Constants.DEFAULT_PHONE_NUMBER_MAX_LENGTH

@Composable
fun PhoneNumberTextField(
    phoneNumberText: String = "",
    countryCodeText: String = "",
    hint: String = "",
    maxLength: Int = DEFAULT_PHONE_NUMBER_MAX_LENGTH,
    keyboardType: KeyboardType = KeyboardType.Phone,
    onValueChange: (String) -> Unit,
    onClearTextClick: () -> Unit,
    modifier: Modifier,
    focusRequester: FocusRequester = FocusRequester()
) {
    Row(
        modifier = modifier
            .border(
                MediumStrokeThickness,
                MaterialTheme.colorScheme.outline,
                RoundedCornerShape(SizeSmall)
            )
            .padding(start = SizeMedium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = countryCodeText,
            onValueChange = {},
            modifier = Modifier.width(IntrinsicSize.Min),
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium
            ),
            enabled = false,
            readOnly = true
        )
        Spacer(modifier = Modifier.width(SizeSmall))
        BasicTextField(
            value = phoneNumberText,
            onValueChange = {},
            modifier = Modifier.weight(1f),
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium
            )
        )
        if (phoneNumberText.isNotBlank()) {
            IconButton(onClick = onClearTextClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_circle_xmark),
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = "",
                    modifier = Modifier.size(SizeMedium)
                )
            }
        }
    }
}