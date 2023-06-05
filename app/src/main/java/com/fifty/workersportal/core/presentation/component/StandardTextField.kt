package com.fifty.workersportal.core.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall

@Composable
fun StandardTextField(
    modifier: Modifier = Modifier,
    basicTextFieldModifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    hint: String,
    titleHint: Boolean = false,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    cursorColor: Color = MaterialTheme.colorScheme.primary,
    value: String,
    maxLength: Int = 400,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    maxLines: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    Column(
        modifier = modifier
    ) {
        if (titleHint) {
            Text(
                hint,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = textColor
                )
            )
        }
        Spacer(modifier = Modifier.height(SizeSmall))
        BasicTextField(
            modifier = basicTextFieldModifier
                .background(backgroundColor, MaterialTheme.shapes.medium)
                .padding(horizontal = SizeSmall),
            value = value,
            onValueChange = {
                if (it.length < maxLength) {
                    onValueChange(it)
                }
            },
            readOnly = readOnly,
            singleLine = singleLine,
            cursorBrush = SolidColor(cursorColor),
            textStyle = MaterialTheme.typography.bodyLarge,
            decorationBox = { innerTextField ->
                Row(
                    basicTextFieldModifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (leadingIcon != null) leadingIcon()
                    Spacer(modifier = Modifier.width(10.dp))
                    Box(Modifier.weight(1f)) {
                        if (value.isEmpty()) Text(
                            hint,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = textColor
                            )
                        )
                        innerTextField()
                    }
                    if (trailingIcon != null) trailingIcon()
                }
            }
        )
    }
}