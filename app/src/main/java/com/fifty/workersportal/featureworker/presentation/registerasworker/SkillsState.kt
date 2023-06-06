package com.fifty.workersportal.featureworker.presentation.registerasworker

import com.fifty.workersportal.featureworker.domain.model.WorkerCategory

data class SkillsState(
    val skills: List<WorkerCategory> = emptyList(),
    val selectedSkills: List<WorkerCategory> = emptyList()
)
