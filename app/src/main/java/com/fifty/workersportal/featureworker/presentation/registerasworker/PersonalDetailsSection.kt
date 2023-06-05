package com.fifty.workersportal.featureworker.presentation.registerasworker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.times
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.SecondaryHeader
import com.fifty.workersportal.core.presentation.component.StandardTextField
import com.fifty.workersportal.core.presentation.ui.theme.MediumButtonHeight
import com.fifty.workersportal.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall
import com.fifty.workersportal.featureworker.presentation.component.OpenToWorkSwitch

@Composable
fun PersonalDetailsSection(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        item {
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = SizeMedium
                    )
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(SizeExtraSmall))
                OpenToWorkSwitch(modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(SizeExtraSmall))
                SecondaryHeader(
                    text = stringResource(R.string.enter_your_personal_info),
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
                            hint = stringResource(R.string.first_name),
                            value = "",
                            onValueChange = {},
                            titleHint = true,
                            keyboardType = KeyboardType.Decimal
                        )
                    }
                    Spacer(modifier = Modifier.width(SizeMedium))
                    Box(modifier = Modifier.weight(1f)) {
                        StandardTextField(
                            basicTextFieldModifier = Modifier
                                .height(MediumButtonHeight)
                                .fillMaxWidth(),
                            hint = stringResource(R.string.last_name),
                            value = "",
                            onValueChange = {},
                            titleHint = true,
                            keyboardType = KeyboardType.Decimal
                        )
                    }
                }
                Spacer(modifier = Modifier.height(SizeMedium))
                StandardTextField(
                    basicTextFieldModifier = Modifier
                        .height(MediumButtonHeight)
                        .fillMaxWidth(),
                    hint = stringResource(R.string.email),
                    value = "",
                    onValueChange = {},
                    titleHint = true,
                    keyboardType = KeyboardType.Email
                )
                Spacer(modifier = Modifier.height(SizeMedium))
                StandardTextField(
                    basicTextFieldModifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = MediumButtonHeight, max = (3f * MediumButtonHeight))
                        .padding(vertical = SizeSmall),
                    hint = stringResource(R.string.bio),
                    value = "",
                    onValueChange = {},
                    titleHint = true,
                    singleLine = false,
                )
                Spacer(modifier = Modifier.height(SizeMedium))
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        StandardTextField(
                            basicTextFieldModifier = Modifier
                                .height(MediumButtonHeight)
                                .fillMaxWidth(),
                            hint = stringResource(R.string.gender),
                            value = "",
                            onValueChange = {},
                            titleHint = true,
                            keyboardType = KeyboardType.Decimal
                        )
                    }
                    Spacer(modifier = Modifier.width(SizeMedium))
                    Box(modifier = Modifier.weight(1f)) {
                        StandardTextField(
                            basicTextFieldModifier = Modifier
                                .height(MediumButtonHeight)
                                .fillMaxWidth(),
                            hint = stringResource(R.string.age),
                            value = "",
                            onValueChange = {},
                            titleHint = true,
                            keyboardType = KeyboardType.Number,
                            maxLength = 2
                        )
                    }
                }
            }
        }
    }
}