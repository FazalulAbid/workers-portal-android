package com.fifty.fixitnow.featurelocation.presentation.detectcurrentlocation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.component.HorizontalDivider
import com.fifty.fixitnow.core.presentation.component.Keyboard
import com.fifty.fixitnow.core.presentation.component.PrimaryButton
import com.fifty.fixitnow.core.presentation.component.SecondaryHeader
import com.fifty.fixitnow.core.presentation.component.StandardOutlinedTextField
import com.fifty.fixitnow.core.presentation.component.keyboardAsState
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.presentation.ui.theme.SizeSmall
import com.fifty.fixitnow.featureworker.presentation.component.Chip
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetectCurrentLocationBottomSheetContent(
    viewModel: DetectCurrentLocationViewModel,
    completeAddressFocusRequester: FocusRequester,
    addressTitleFocusRequester: FocusRequester,
    floorFocusRequester: FocusRequester,
    landmarkFocusRequester: FocusRequester,
    isLoading: Boolean,
    onSaveAddressClick: () -> Unit
) {
    val context = LocalContext.current
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()
    val keyboardOpenState by keyboardAsState()

    LaunchedEffect(key1 = keyboardOpenState) {
        if (keyboardOpenState == Keyboard.Opened) {
            bringIntoViewRequester.bringIntoView()
        }
    }

    Column(
        modifier = Modifier
            .padding(horizontal = SizeMedium),
    ) {
        SecondaryHeader(
            text = stringResource(R.string.enter_complete_address),
            style = MaterialTheme.typography.titleMedium
        )
        HorizontalDivider(verticalPadding = true)
        Text(
            text = stringResource(R.string.save_address_as),
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
            )
        )
        Spacer(modifier = Modifier.height(SizeSmall))
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(viewModel.saveAddressAsItems) { addressAsText ->
                Chip(
                    selected = viewModel.addressTitleState.value.text == addressAsText,
                    onChipClick = {
                        viewModel.onEvent(
                            DetectCurrentLocationEvent.SelectSaveAddressAs(
                                addressAsText
                            )
                        )
                    },
                    text = addressAsText,
                    textStyle = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(SizeSmall))
            }
        }
        Spacer(modifier = Modifier.height(SizeMedium))
        StandardOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusEvent { event ->
                    if (event.isFocused) {
                        coroutineScope.launch {
                            bringIntoViewRequester.bringIntoView()
                        }
                    }
                },
            hint = stringResource(R.string.save_address_as),
            value = viewModel.addressTitleState.value.text,
            onValueChange = {
                viewModel.onEvent(DetectCurrentLocationEvent.EnterAddressTitle(it))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Words
            ),
            keyboardActions = KeyboardActions(
                onNext = { completeAddressFocusRequester.requestFocus() }
            ),
            focusRequester = addressTitleFocusRequester
        )
        Spacer(modifier = Modifier.height(SizeSmall))
        StandardOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusEvent { event ->
                    if (event.isFocused) {
                        coroutineScope.launch {
                            bringIntoViewRequester.bringIntoView()
                        }
                    }
                },
            hint = stringResource(R.string.complete_address),
            value = viewModel.completeAddressState.value.text,
            onValueChange = {
                viewModel.onEvent(DetectCurrentLocationEvent.EnterCompleteAddress(it))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Words
            ),
            keyboardActions = KeyboardActions(
                onNext = { floorFocusRequester.requestFocus() }
            ),
            focusRequester = completeAddressFocusRequester
        )
        Spacer(modifier = Modifier.height(SizeSmall))
        StandardOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusEvent { event ->
                    if (event.isFocused) {
                        coroutineScope.launch {
                            bringIntoViewRequester.bringIntoView()
                        }
                    }
                },
            hint = "${stringResource(R.string.floor)} (${stringResource(R.string.optional)})",
            value = viewModel.floorState.value.text,
            onValueChange = {
                viewModel.onEvent(DetectCurrentLocationEvent.EnterFloor(it))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Words
            ),
            keyboardActions = KeyboardActions(
                onNext = { landmarkFocusRequester.requestFocus() }
            ),
            focusRequester = floorFocusRequester
        )
        Spacer(modifier = Modifier.height(SizeSmall))
        StandardOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusEvent { event ->
                    if (event.isFocused) {
                        coroutineScope.launch {
                            bringIntoViewRequester.bringIntoView()
                        }
                    }
                },
            hint = "${stringResource(R.string.nearby_landmark)} (${stringResource(R.string.optional)})",
            value = viewModel.landmarkState.value.text,
            onValueChange = {
                viewModel.onEvent(DetectCurrentLocationEvent.EnterLandmark(it))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.Words
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            focusRequester = landmarkFocusRequester
        )
        HorizontalDivider(verticalPadding = true)
        PrimaryButton(
            modifier = Modifier.bringIntoViewRequester(bringIntoViewRequester),
            text = stringResource(R.string.save_address),
            onClick = onSaveAddressClick,
            isLoading = isLoading
        )
    }
}