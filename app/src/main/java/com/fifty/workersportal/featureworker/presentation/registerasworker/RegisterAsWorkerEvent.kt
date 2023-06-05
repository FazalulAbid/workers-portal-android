package com.fifty.workersportal.featureworker.presentation.registerasworker

sealed class RegisterAsWorkerEvent {
    data class ToggleOpenToWork(val isOpen: Boolean) : RegisterAsWorkerEvent()
    data class EnterFirstName(val firstName: String) : RegisterAsWorkerEvent()
    data class EnterLastName(val lastName: String) : RegisterAsWorkerEvent()
    data class EnterEmail(val email: String) : RegisterAsWorkerEvent()
    data class EnterBio(val bio: String) : RegisterAsWorkerEvent()
    data class EnterGender(val gender: String) : RegisterAsWorkerEvent()
    data class EnterAge(val age: String) : RegisterAsWorkerEvent()
}
