package com.fifty.fixitnow.featureworker.presentation.registerasworker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.component.SecondaryHeader
import com.fifty.fixitnow.core.presentation.ui.theme.SizeLarge
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.featureworker.domain.model.WorkerCategory
import com.fifty.fixitnow.featureworker.presentation.component.Chip
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment

@Composable
fun SelectSkillsSection(
    modifier: Modifier = Modifier,
    viewModel: RegisterAsWorkerViewModel
) {
    Column(
        modifier = modifier
            .padding(
                horizontal = SizeMedium
            )
            .fillMaxSize()
    ) {
        SecondaryHeader(
            modifier = Modifier.padding(vertical = SizeMedium),
            text = stringResource(R.string.select_your_skills),
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold
            ),
        )
        Text(
            text = stringResource(id = R.string.you_can_customize_your_wages_from_the_next_page),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(SizeLarge))
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            mainAxisAlignment = MainAxisAlignment.Start,
            mainAxisSpacing = SizeMedium,
            crossAxisSpacing = SizeMedium
        ) {
            // Sample data
            val workerCategories = listOf(
                WorkerCategory("1", "Plumber", "Plumbing", "", 500.0f, 600.0f, "0.0", "0.0"),
                WorkerCategory("2", "Cleaner", "Cleaning", "", 400.0f, 550.0f, "0.0", "0.0"),
                WorkerCategory("3", "Electrician", "Electrical", "", 600.0f, 700.0f, "0.0", "0.0"),
                WorkerCategory("4", "Carpenter", "Carpentry", "", 450.0f, 550.0f, "0.0", "0.0"),
                WorkerCategory("5", "Gardener", "Gardening", "", 550.0f, 650.0f, "0.0", "0.0"),
                WorkerCategory("6", "Painter", "Painting", "", 500.0f, 600.0f, "0.0", "0.0"),
                WorkerCategory("7", "Mover", "Moving", "", 400.0f, 500.0f, "0.0", "0.0"),
                WorkerCategory("8", "Tutor", "Tutoring", "", 600.0f, 700.0f, "0.0", "0.0"),
                WorkerCategory("9", "Cook", "Cooking", "", 550.0f, 650.0f, "0.0", "0.0"),
                WorkerCategory("10", "Mechanic", "Mechanical", "", 500.0f, 600.0f, "0.0", "0.0"),
                WorkerCategory(
                    "11",
                    "IT Technician",
                    "IT Services",
                    "",
                    700.0f,
                    800.0f,
                    "0.0",
                    "0.0"
                ),
                WorkerCategory("12", "Plasterer", "Plastering", "", 400.0f, 500.0f, "0.0", "0.0")
            )

            viewModel.skillsState.value.skills.forEach { workerCategory ->
                Chip(
                    text = workerCategory.title ?: "",
                    selected = viewModel.skillsState.value.selectedSkills.any { it.title == workerCategory.title },
                    onChipClick = {
                        viewModel.onEvent(RegisterAsWorkerEvent.SetSkillSelected(workerCategory))
                    }
                )
            }
        }
    }
}