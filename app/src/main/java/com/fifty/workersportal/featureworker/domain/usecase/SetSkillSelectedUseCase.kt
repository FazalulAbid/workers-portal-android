package com.fifty.workersportal.featureworker.domain.usecase

import com.fifty.workersportal.R
import com.fifty.workersportal.core.util.Resource
import com.fifty.workersportal.core.util.UiText
import com.fifty.workersportal.featureworker.domain.model.WorkerCategory
import com.fifty.workersportal.featureworker.domain.util.WorkerConstants

class SetSkillSelectedUseCase {

    operator fun invoke(
        selectedSkills: List<WorkerCategory>,
        skillToToggle: WorkerCategory
    ): Resource<List<WorkerCategory>> {
        val skillInList = selectedSkills.find { it.name == skillToToggle.name }
        if (skillInList != null) {
            return Resource.Success(selectedSkills - skillInList)
        }
        return if (selectedSkills.size >= WorkerConstants.MAX_SELECTED_SKILL_COUNT) {
            Resource.Error(
                uiText = UiText.StringResource(
                    R.string.error_max_skills_selected
                )
            )
        } else {
            Resource.Success(selectedSkills + skillToToggle)
        }
    }
}