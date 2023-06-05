package com.fifty.workersportal.featureworker.presentation.registerasworker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.SecondaryHeader
import com.fifty.workersportal.core.presentation.component.StandardTextField
import com.fifty.workersportal.core.presentation.ui.theme.MediumButtonHeight
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium

@Composable
fun SkillWagesSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(
                horizontal = SizeMedium
            )
            .fillMaxSize()
    ) {
        SecondaryHeader(
            text = stringResource(R.string.enter_your_wages),
            style = MaterialTheme.typography.titleMedium
        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(modifier = Modifier.weight(1f)) {
                StandardTextField(
                    basicTextFieldModifier = Modifier
                        .height(MediumButtonHeight)
                        .fillMaxWidth(),
                    hint = stringResource(R.string.daily_wage),
                    value = "",
                    onValueChange = {},
                    titleHint = true,
                    maxLines = 3,
                    keyboardType = KeyboardType.Decimal,
                    maxLength = 6
                )
            }
            Spacer(modifier = Modifier.width(SizeMedium))
            Box(modifier = Modifier.weight(1f)) {
                StandardTextField(
                    basicTextFieldModifier = Modifier
                        .height(MediumButtonHeight)
                        .fillMaxWidth(),
                    hint = stringResource(R.string.hourly_wage),
                    value = "",
                    onValueChange = {},
                    titleHint = true,
                    maxLines = 3,
                    keyboardType = KeyboardType.Decimal,
                    maxLength = 6
                )
            }
        }
    }
}