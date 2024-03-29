package com.fifty.fixitnow.featureworker.presentation.registerasworker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.component.SecondaryHeader
import com.fifty.fixitnow.core.presentation.component.StandardTextField
import com.fifty.fixitnow.core.presentation.ui.theme.MediumButtonHeight
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.presentation.ui.theme.SizeSmall

@Composable
fun SkillWagesSection(
    modifier: Modifier = Modifier,
    viewModel: RegisterAsWorkerViewModel,
    focusManager: FocusManager
) {
    val selectedSkills = viewModel.skillsState.value.selectedSkills

    Column(
        modifier = modifier
            .padding(horizontal = SizeMedium)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.enter_your_wages),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier
                    .padding(vertical = SizeMedium)
                    .weight(1f)
            )
            Button(
                onClick = { viewModel.onEvent(RegisterAsWorkerEvent.SetDefaultWages) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(text = stringResource(R.string.set_default_wages))
            }
        }
        LazyColumn {
            items(selectedSkills.size) { index ->
                val skill = selectedSkills[index]
                SecondaryHeader(
                    modifier = Modifier.padding(vertical = SizeSmall),
                    text = skill.skill ?: "",
                    style = MaterialTheme.typography.titleSmall
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
                            value = skill.dailyWage,
                            onValueChange = {
                                viewModel.onEvent(
                                    RegisterAsWorkerEvent.EnterSelectedSkillDailyWage(index, it)
                                )
                            },
                            titleHint = true,
                            maxLines = 3,
                            maxLength = 6,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Decimal,
                                imeAction = ImeAction.Next,
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Next) }
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(SizeMedium))
                    Box(modifier = Modifier.weight(1f)) {
                        StandardTextField(
                            basicTextFieldModifier = Modifier
                                .height(MediumButtonHeight)
                                .fillMaxWidth(),
                            hint = stringResource(R.string.hourly_wage),
                            value = skill.hourlyWage,
                            onValueChange = {
                                viewModel.onEvent(
                                    RegisterAsWorkerEvent.EnterSelectedSkillHourlyWage(index, it)
                                )
                            },
                            titleHint = true,
                            maxLines = 3,
                            maxLength = 6,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Decimal,
                                imeAction = ImeAction.Next,
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Next) }
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(SizeMedium))
            }
        }
    }
}