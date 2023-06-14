package com.fifty.workersportal.core.presentation.component

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

@Composable
fun StandardOutlinedTextField(
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    hint: String,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    cursorColor: Color = MaterialTheme.colorScheme.primary,
    value: String,
    shape: Shape = MaterialTheme.shapes.medium,
    maxLength: Int = 400,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    maxLines: Int = 1,
    keyboardActions: KeyboardActions = KeyboardActions(),
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    focusRequester: FocusRequester = FocusRequester()
) {
    OutlinedTextField(
        modifier = modifier
            .focusRequester(focusRequester),
        label = {
            Text(
                text = hint,
                style = MaterialTheme.typography.bodyMedium
                    .copy(color = MaterialTheme.colorScheme.onSurface)
            )
        },
        value = value,
        onValueChange = onValueChange,
        shape = shape,
        maxLines = maxLines,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        readOnly = readOnly,
        singleLine = singleLine,
        textStyle = MaterialTheme.typography.bodyMedium
            .copy(color = MaterialTheme.colorScheme.onBackground),
    )
}