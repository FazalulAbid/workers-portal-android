package com.fifty.workersportal.featureauth.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.KeyboardType
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.StrokeThickness
import com.fifty.workersportal.core.util.Constants.DEFAULT_PHONE_NUMBER_MAX_LENGTH
import org.w3c.dom.Text

@Composable
fun PhoneNumberTextField(
    text: String = "",
    hint: String = "",
    maxLength: Int = DEFAULT_PHONE_NUMBER_MAX_LENGTH,
    keyboardType: KeyboardType = KeyboardType.Phone,
    onValueChange: (String) -> Unit,
    onClearTextClick: () -> Unit,
    modifier: Modifier,
    focusRequester: FocusRequester = FocusRequester()
) {
    Box(
        modifier = modifier
            .border(
                StrokeThickness,
                MaterialTheme.colorScheme.outline,
                RoundedCornerShape(SizeMedium)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "hia")
    }
}