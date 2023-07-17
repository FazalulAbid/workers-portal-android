package com.fifty.fixitnow.featureworker.domain.usecase

import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.util.Constants
import com.fifty.fixitnow.core.util.Resource
import com.fifty.fixitnow.core.util.UiText
import com.fifty.fixitnow.featureworker.domain.model.WorkerCategory

class SetSkillSelectedUseCase {

    operator fun invoke(
        selectedSkills: List<WorkerCategory>,
        skillToToggle: WorkerCategory
    ): Resource<List<WorkerCategory>> {
        val skillInList = selectedSkills.find { it.title == skillToToggle.title }
        if (skillInList != null) {
            return Resource.Success(selectedSkills - skillInList)
        }
        return if (selectedSkills.size >= Constants.MAX_SELECTED_SKILL_COUNT) {
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