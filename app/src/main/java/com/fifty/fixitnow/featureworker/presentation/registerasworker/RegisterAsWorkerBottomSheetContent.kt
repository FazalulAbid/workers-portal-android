package com.fifty.fixitnow.featureworker.presentation.registerasworker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.component.PrimaryButton
import com.fifty.fixitnow.core.presentation.component.SecondaryHeader
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.featureworker.domain.model.WorkerCategory
import com.fifty.fixitnow.featureworker.presentation.component.Chip
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment

@Composable
fun RegisterAsWorkerBottomSheetContent(
    chosenSkills: List<WorkerCategory>,
    primarySkillSelectedId: String?,
    setSelected: (WorkerCategory) -> Unit,
    onSaveChanges: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(bottom = SizeMedium, start = SizeMedium, end = SizeMedium),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column() {
            SecondaryHeader(
                modifier = Modifier.padding(vertical = SizeMedium),
                text = stringResource(R.string.select_your_primary_skill),
                style = MaterialTheme.typography.titleMedium
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                mainAxisAlignment = MainAxisAlignment.Start,
                mainAxisSpacing = SizeMedium,
                crossAxisSpacing = SizeMedium
            ) {
                SelectPrimarySkillChips(
                    items = chosenSkills,
                    primarySkillSelectedId,
                    setSelected
                )
            }
        }
        Spacer(modifier = Modifier.height(SizeMedium))
        PrimaryButton(text = "Save changes") {
            onSaveChanges()
        }
    }
}

@Composable
fun SelectPrimarySkillChips(
    items: List<WorkerCategory>,
    selectedPrimarySkillId: String?,
    setSelected: (selected: WorkerCategory) -> Unit,
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            mainAxisAlignment = MainAxisAlignment.Start,
            mainAxisSpacing = SizeMedium,
            crossAxisSpacing = SizeMedium
        ) {
            items.forEach { workerCategory ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Chip(
                        selected = selectedPrimarySkillId == workerCategory.id,
                        onChipClick = {
                            setSelected(workerCategory)
                        },
                        text = workerCategory.skill ?: ""
                    )
                }
            }
        }
    }
}