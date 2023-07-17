package com.fifty.fixitnow.featureworker.presentation.registerasworker

import com.fifty.fixitnow.featureworker.domain.model.WorkerCategory
import java.lang.Error

data class SkillsState(
    val skills: List<WorkerCategory> = emptyList(),
    val selectedSkills: List<WorkerCategory> = emptyList(),
    val error: Error? = null
)
