package com.fifty.workersportal.core.presentation.component

import androidx.compose.foundation.background
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.fifty.workersportal.di.AppModule_ProvideGetOwnUserIdFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StandardDropDownMenu(
    modifier: Modifier = Modifier,
    expandedState: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    options: List<String>,
    hint: String,
    isTitleHintNeeded: Boolean
) {
    ExposedDropdownMenuBox(
        expanded = expandedState,
        onExpandedChange = onExpandedChange
    ) {
        StandardTextField(
            basicTextFieldModifier = modifier,
            modifier = Modifier.menuAnchor(),
            value = selectedOption,
            onValueChange = {},
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expandedState
                )
            },
            readOnly = true,
            hint = hint,
            titleHint = isTitleHintNeeded
        )
        ExposedDropdownMenu(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            expanded = expandedState,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background),
                    text = {
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    },
                    onClick = {
                        onOptionSelected(option)
                        onExpandedChange(false)
                    }
                )
            }
        }
    }
}