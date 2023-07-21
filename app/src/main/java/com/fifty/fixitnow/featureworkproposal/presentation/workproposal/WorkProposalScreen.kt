package com.fifty.fixitnow.featureworkproposal.presentation.workproposal

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.ImageLoader
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.component.PrimaryButton
import com.fifty.fixitnow.core.presentation.component.SecondaryHeader
import com.fifty.fixitnow.core.presentation.component.StandardAppBar
import com.fifty.fixitnow.core.presentation.component.StandardMultilineTextField
import com.fifty.fixitnow.core.presentation.ui.theme.MediumButtonHeight
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.presentation.util.ToastExt
import com.fifty.fixitnow.core.presentation.util.UiEvent
import com.fifty.fixitnow.core.util.Screen
import com.fifty.fixitnow.core.util.toDate
import com.fifty.fixitnow.featureworker.presentation.component.ANIMATION_DURATION
import com.fifty.fixitnow.featureworkproposal.data.util.WorkProposalError
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import kotlinx.coroutines.flow.collectLatest
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
    popBackStackUpTo: (route: String, inclusive: Boolean) -> Unit,
    imageLoader: ImageLoader,
    viewModel: WorkProposalViewModel
) {
    val state = viewModel.state.value
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

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.SentWorkProposal -> {
                    ToastExt.makeText(
                        context = context,
                        message = R.string.work_proposal_sent_successfully
                    )
                    viewModel.isWorkProposalSentDialogDisplayed.postValue(true)
                }

                else -> Unit
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.errorFlow.collectLatest { error ->
            when (error) {
                WorkProposalError.WorkerDateConflict -> {
                    ToastExt.makeText(
                        context = context,
                        message = R.string.worker_date_conflict_message
                    )
                }

                WorkProposalError.InvalidDate -> {
                    ToastExt.makeText(
                        context = context,
                        message = R.string.choose_a_date_for_work_proposal
                    )
                }

                WorkProposalError.InvalidWage -> {
                    ToastExt.makeText(
                        context = context,
                        message = R.string.choose_a_valid_wage
                    )
                }

                WorkProposalError.InvalidWorkAddress -> {
                    ToastExt.makeText(
                        context = context,
                        message = R.string.select_an_address_for_work
                    )
                }

                WorkProposalError.InvalidWorkCategory -> {
                    ToastExt.makeText(
                        context = context,
                        message = R.string.choose_a_category_for_work
                    )
                }

                WorkProposalError.InvalidWorkDescription -> {
                    ToastExt.makeText(
                        context = context,
                        message = "Enter a valid work description"
                    )
                }

                WorkProposalError.InvalidWorker -> {
                    ToastExt.makeText(
                        context = context,
                        message = R.string.select_a_worker
                    )
                }

                WorkProposalError.WorkerDateConflict -> {
                    ToastExt.makeText(
                        context = context,
                        message = R.string.worker_is_not_available_on_selected_date_please_choose_another_worker
                    )
                }
            }
        }
    }

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
                    viewModel.workerState.value?.let { worker ->
                        ChosenWorkerCard(
                            worker = worker,
                            chosenCategoryName = viewModel.workCategory.value?.title ?: "",
                            chosenWage = if (viewModel.isFullDay.value) {
                                viewModel.workCategory.value?.dailyWage ?: ""
                            } else viewModel.workCategory.value?.hourlyWage ?: "",
                            imageLoader = imageLoader
                        )
                    }
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
                    viewModel.onEvent(WorkProposalEvent.SendProposal)
                }
            )
        }
    }

    val isWorkProposalSentDialogDisplayed by viewModel
        .isWorkProposalSentDialogDisplayed
        .observeAsState(false)

    AnimatedVisibility(
        visible = isWorkProposalSentDialogDisplayed
    ) {
        Dialog(
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            ),
            onDismissRequest = {
                viewModel.isWorkProposalSentDialogDisplayed.postValue(false)
                popBackStackUpTo(Screen.UserDashboardScreen.route, false)
            }
        ) {
            state.workProposal?.let { workProposal ->
                viewModel.workerState.value?.let { worker ->
                    WorkProposalSentDialogContent(
                        title = stringResource(R.string.work_proposal_sent),
                        workProposal = workProposal,
                        buttonText = stringResource(R.string.go_to_dashboard),
                        worker = worker,
                        onButtonClick = {
                            viewModel.isWorkProposalSentDialogDisplayed.postValue(false)
                            popBackStackUpTo(Screen.UserDashboardScreen.route, false)
                        }
                    )
                }
            }
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