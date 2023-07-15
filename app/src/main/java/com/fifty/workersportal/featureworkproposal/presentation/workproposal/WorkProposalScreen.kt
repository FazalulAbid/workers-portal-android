package com.fifty.workersportal.featureworkproposal.presentation.workproposal

import android.annotation.SuppressLint
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.times
import coil.ImageLoader
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.PrimaryButton
import com.fifty.workersportal.core.presentation.component.SecondaryHeader
import com.fifty.workersportal.core.presentation.component.StandardAppBar
import com.fifty.workersportal.core.presentation.component.StandardMultilineTextField
import com.fifty.workersportal.core.presentation.ui.theme.MediumButtonHeight
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.util.toDate
import com.fifty.workersportal.featurelocation.domain.model.LocalAddress
import com.fifty.workersportal.featureworker.domain.model.Worker
import com.fifty.workersportal.featureworker.domain.model.WorkerCategory
import com.fifty.workersportal.featureworker.presentation.component.ANIMATION_DURATION
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@SuppressLint("UnusedTransitionTargetStateParameter")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WorkProposalScreen(
    onNavigate: (String) -> Unit,
    onNavigateUp: () -> Unit,
    imageLoader: ImageLoader,
    viewModel: WorkProposalViewModel
) {
    val context = LocalContext.current
    val calenderState = rememberUseCaseState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val transitionState = remember {
        MutableTransitionState(viewModel.isFullDay.value).apply {
            targetState = !viewModel.isFullDay.value
        }
    }
    val transition = updateTransition(transitionState, "isFullDayTransition")
    val isFullDayColor by transition.animateColor(
        label = "",
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        targetValueByState = {
            if (viewModel.isFullDay.value)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onSurface
        }
    )
    val isHalfDayColor by transition.animateColor(
        label = "",
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        targetValueByState = {
            if (viewModel.isFullDay.value)
                MaterialTheme.colorScheme.onSurface
            else
                MaterialTheme.colorScheme.secondary
        }
    )
    val isBeforeNoonColor by transition.animateColor(
        label = "",
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        targetValueByState = {
            if (viewModel.isBeforeNoon.value)
                MaterialTheme.colorScheme.onSurface
            else
                MaterialTheme.colorScheme.secondary
        }
    )
    val isAfterNoonColor by transition.animateColor(
        label = "",
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        targetValueByState = {
            if (viewModel.isBeforeNoon.value)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onSurface
        }
    )

    Column(Modifier.fillMaxSize()) {
        StandardAppBar(
            onNavigateUp = onNavigateUp,
            showBackArrow = true,
            title = {
                Text(
                    text = stringResource(R.string.confirm_work_proposal),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        )
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(SizeMedium)
        ) {
            item {
                Column {
                    SecondaryHeader(
                        text = stringResource(R.string.chosen_date),
                        modifier = Modifier.padding(bottom = SizeMedium),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                MaterialTheme.colorScheme.surface,
                                MaterialTheme.shapes.medium
                            )
                            .padding(SizeMedium),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = formatDateForChosenWork(viewModel.proposalDateState.value)
                                ?: stringResource(R.string.choose_a_date),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = if (viewModel.proposalDateState.value == null) {
                                    MaterialTheme.colorScheme.onSurface
                                } else MaterialTheme.colorScheme.onBackground,
                                fontWeight = if (viewModel.proposalDateState.value == null) {
                                    FontWeight.Normal
                                } else FontWeight.SemiBold
                            )
                        )
                        IconButton(onClick = {
                            calenderState.show()
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_calander),
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    SecondaryHeader(
                        text = stringResource(R.string.chosen_worker),
                        modifier = Modifier.padding(vertical = SizeMedium),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                    ChosenWorkerCard(
                        worker = Worker(
                            workerId = "1234567890",
                            firstName = "John",
                            lastName = "Doe",
                            isVerified = true,
                            gender = "Male",
                            bio = "I am an experienced worker with various skills.",
                            categoryList = listOf(
                                WorkerCategory(
                                    id = "1",
                                    title = "Plumber",
                                    skill = "Plumbing",
                                    imageUrl = "https://example.com/plumber.jpg",
                                    dailyMinWage = 50.0f,
                                    hourlyMinWage = 10.0f,
                                    dailyWage = "50 USD",
                                    hourlyWage = "10 USD/h"
                                ),
                                WorkerCategory(
                                    id = "2",
                                    title = "Electrician",
                                    skill = "Electrical Work",
                                    imageUrl = "https://example.com/electrician.jpg",
                                    dailyMinWage = 60.0f,
                                    hourlyMinWage = 12.0f,
                                    dailyWage = "60 USD",
                                    hourlyWage = "12 USD/h"
                                )
                            ),
                            openToWork = true,
                            userId = "9876543210",
                            profileImageUrl = "https://example.com/profile.jpg",
                            ratingAverage = 4.5f,
                            ratingCount = 100,
                            localAddress = LocalAddress(
                                id = "abcd1234",
                                title = "Home",
                                completeAddress = "123 Main Street",
                                floor = "2nd Floor",
                                landmark = "Central Park",
                                place = "New York",
                                subLocality = "Manhattan",
                                city = "New York City",
                                state = "New York",
                                country = "USA",
                                pin = "12345",
                                location = listOf(40.7128, -74.0060)
                            ),
                            isFavourite = false,
                            primaryCategoryId = "1",
                            distance = 5.3
                        ),
                        imageLoader = imageLoader
                    )
                    SecondaryHeader(
                        text = stringResource(R.string.enter_work_description),
                        modifier = Modifier.padding(vertical = SizeMedium),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                    StandardMultilineTextField(
                        basicTextFieldModifier = Modifier
                            .fillMaxWidth()
                            .heightIn(
                                min = (3f * MediumButtonHeight),
                                max = (5f * MediumButtonHeight)
                            ),
                        hint = stringResource(R.string.bio),
                        value = viewModel.workDescriptionState.value.text,
                        onValueChange = {
                            viewModel.onEvent(WorkProposalEvent.InputWorkDescription(it))
                        },
                        textColor = MaterialTheme.colorScheme.onSurface,
                        singleLine = false,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done,
                            capitalization = KeyboardCapitalization.Sentences
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                keyboardController?.hide()
                            }
                        )
                    )
                    SecondaryHeader(
                        text = stringResource(R.string.work_time),
                        modifier = Modifier.padding(vertical = SizeMedium),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                MaterialTheme.colorScheme.surface,
                                MaterialTheme.shapes.medium
                            )
                            .padding(SizeMedium),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Half Day",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Medium,
                                color = isHalfDayColor
                            )
                        )
                        Switch(
                            colors = SwitchDefaults.colors(
                                uncheckedTrackColor = isHalfDayColor,
                                checkedTrackColor = isFullDayColor
                            ),
                            checked = viewModel.isFullDay.value,
                            onCheckedChange = {
                                viewModel.onEvent(WorkProposalEvent.ChangeIsFullDay(it))
                            }
                        )
                        Text(
                            text = "Full Day",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Medium,
                                color = isFullDayColor
                            )
                        )
                    }
                    SecondaryHeader(
                        text = stringResource(R.string.half_day_details),
                        modifier = Modifier.padding(vertical = SizeMedium),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                MaterialTheme.colorScheme.surface,
                                MaterialTheme.shapes.medium
                            )
                            .padding(SizeMedium),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Before Noon",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Medium,
                                color = if (!viewModel.isFullDay.value)
                                    isBeforeNoonColor
                                else MaterialTheme.colorScheme.onSurface
                            )
                        )
                        Switch(
                            enabled = !viewModel.isFullDay.value,
                            colors = SwitchDefaults.colors(
                                uncheckedTrackColor = isBeforeNoonColor,
                                checkedTrackColor = isAfterNoonColor
                            ),
                            checked = viewModel.isBeforeNoon.value,
                            onCheckedChange = {
                                viewModel.onEvent(WorkProposalEvent.ChangeIsBeforeNoon(it))
                            }
                        )
                        Text(
                            text = "After Noon",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Medium,
                                color = if (!viewModel.isFullDay.value)
                                    isAfterNoonColor
                                else MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(SizeMedium))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = SizeMedium, start = SizeMedium, end = SizeMedium)
        ) {
            PrimaryButton(
                text = stringResource(R.string.send_proposal),
                onClick = {

                }
            )
        }
    }

    val rangeStartDate = LocalDate.now().plusDays(1)
    val rangeEndYearOffset = 1L
    val rangeEndDate = LocalDate.now().plusYears(rangeEndYearOffset)
        .withMonth(1)
        .withDayOfMonth(15)
    val range = rangeStartDate..rangeEndDate

    CalendarDialog(
        state = calenderState,
        config = CalendarConfig(
            monthSelection = true,
            boundary = range
        ),
        selection = CalendarSelection.Date { date ->
            viewModel.onEvent(WorkProposalEvent.InputProposalDate(date))
            calenderState.hide()
        }
    )
}

fun formatDateForChosenWork(localDate: LocalDate?): String? {
    return localDate?.let {
        val date = it.toDate()
        val formattedDate = SimpleDateFormat("dd, MMMM yyyy", Locale.getDefault()).format(date)
        val day = it.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
        "$formattedDate${if (day.isNotBlank()) ", $day" else ""}"
    }
}