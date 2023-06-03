package com.fifty.workersportal.featureworker.presentation.registerasworker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.times
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.SecondaryHeader
import com.fifty.workersportal.core.presentation.component.StandardAppBar
import com.fifty.workersportal.core.presentation.component.StandardTextField
import com.fifty.workersportal.core.presentation.ui.theme.MediumButtonHeight
import com.fifty.workersportal.core.presentation.ui.theme.SizeExtraSmall
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall
import com.fifty.workersportal.featureworker.presentation.component.Chip
import com.fifty.workersportal.featureworker.presentation.component.OpenToWorkSwitch
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import kotlin.random.Random

@Composable
fun RegisterAsWorkerScreen(
    onNavigateUp: () -> Unit = {}
) {
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StandardAppBar(
            onNavigateUp = onNavigateUp,
            showBackArrow = true,
            title = {
                Text(
                    text = stringResource(R.string.register_as_worker),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            navActions = {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_done),
                        contentDescription = stringResource(R.string.save_changes),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )
        LazyColumn {
            item {
                Column(
                    modifier = Modifier
                        .padding(horizontal = SizeMedium)
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
                                modifier = Modifier
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
                                modifier = Modifier
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
                        modifier = Modifier
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
                        modifier = Modifier
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
                                modifier = Modifier
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
                                modifier = Modifier
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
                    Spacer(modifier = Modifier.height(SizeMedium))
                    SecondaryHeader(
                        text = stringResource(R.string.enter_your_wages),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            StandardTextField(
                                modifier = Modifier
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
                                modifier = Modifier
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
                    Spacer(modifier = Modifier.height(SizeMedium))
                    SecondaryHeader(
                        text = stringResource(R.string.select_your_skills),
                        style = MaterialTheme.typography.titleMedium
                    )
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        mainAxisAlignment = MainAxisAlignment.Start,
                        mainAxisSpacing = SizeMedium,
                        crossAxisSpacing = SizeMedium
                    ) {
                        repeat(10) {
                            Chip(
                                text = "Hello there",
                                selected = Random.nextBoolean(),
                                onChipClick = {}
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(SizeMedium))
                }
            }
        }
    }
}