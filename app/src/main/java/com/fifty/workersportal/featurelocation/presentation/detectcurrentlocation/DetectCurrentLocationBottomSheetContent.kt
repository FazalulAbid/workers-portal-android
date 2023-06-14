package com.fifty.workersportal.featurelocation.presentation.detectcurrentlocation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.HorizontalDivider
import com.fifty.workersportal.core.presentation.component.PrimaryButton
import com.fifty.workersportal.core.presentation.component.SecondaryHeader
import com.fifty.workersportal.core.presentation.component.StandardOutlinedTextField
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SizeSmall
import com.fifty.workersportal.featureworker.presentation.component.Chip

@Composable
fun DetectCurrentLocationBottomSheetContent(
    viewModel: DetectCurrentLocationViewModel,
    focusManager: FocusManager,
    completeAddressFocusRequester: FocusRequester,
    addressTitleFocusRequester: FocusRequester,
    onDone: () -> Unit,
    onSaveAddressClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(SizeMedium),
    ) {
        SecondaryHeader(
            modifier = Modifier.weight(1f),
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
                .focusRequester(addressTitleFocusRequester),
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
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            focusRequester = addressTitleFocusRequester
        )
        Spacer(modifier = Modifier.height(SizeSmall))
        StandardOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
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
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            focusRequester = completeAddressFocusRequester
        )
        Spacer(modifier = Modifier.height(SizeSmall))
        StandardOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
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
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )
        Spacer(modifier = Modifier.height(SizeSmall))
        StandardOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
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
                    onDone()
                }
            )
        )
        HorizontalDivider(verticalPadding = true)
        PrimaryButton(
            text = stringResource(R.string.save_address),
            onClick = onSaveAddressClick,
            isLoading = viewModel.state.value.isAddressLoading
        )
    }
}