package com.fifty.fixitnow.featureworker.presentation.registerasworker

import android.net.Uri
import com.fifty.fixitnow.featureworker.domain.model.WorkerCategory

sealed class RegisterAsWorkerEvent {
    data class EnterFirstName(val firstName: String) : RegisterAsWorkerEvent()
    data class EnterLastName(val lastName: String) : RegisterAsWorkerEvent()
    data class EnterEmail(val email: String) : RegisterAsWorkerEvent()
    data class EnterBio(val bio: String) : RegisterAsWorkerEvent()
    data class GenderSelectedChanged(val gender: String) : RegisterAsWorkerEvent()
    data class EnterAge(val age: String) : RegisterAsWorkerEvent()
    data class SetSkillSelected(val workerCategory: WorkerCategory) : RegisterAsWorkerEvent()
    data class PrimarySkillSelected(val workerCategory: WorkerCategory) : RegisterAsWorkerEvent()
    data class CropProfilePicture(val uri: Uri?) : RegisterAsWorkerEvent()
    data class CropIdentityPicture(val uri: Uri?) : RegisterAsWorkerEvent()
    data class EnterSelectedSkillDailyWage(val index: Int, val dailyWage: String) :
        RegisterAsWorkerEvent()

    data class EnterSelectedSkillHourlyWage(val index: Int, val hourlyWage: String) :
        RegisterAsWorkerEvent()

    object OnSaveCheckClick : RegisterAsWorkerEvent()
    object SetDefaultWages : RegisterAsWorkerEvent()
    object ToggleOpenToWork : RegisterAsWorkerEvent()
    object UpdateWorker : RegisterAsWorkerEvent()
}
