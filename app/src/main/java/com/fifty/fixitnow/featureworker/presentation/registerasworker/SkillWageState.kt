package com.fifty.fixitnow.featureworker.presentation.registerasworker

import com.fifty.fixitnow.featureworker.domain.model.WorkerCategory

data class SkillWage(
    val skill: WorkerCategory,
    val dailyWage: Float,
    val hourlyWage: Float
)

data class SkillWageState(
    val skillWages: List<SkillWage>
)
