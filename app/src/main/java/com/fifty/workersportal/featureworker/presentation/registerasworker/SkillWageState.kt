package com.fifty.workersportal.featureworker.presentation.registerasworker

import com.fifty.workersportal.featureworker.domain.model.WorkerCategory

data class SkillWage(
    val skill: WorkerCategory,
    val dailyWage: Float,
    val hourlyWage: Float
)

data class SkillWageState(
    val skillWages: List<SkillWage>
)
