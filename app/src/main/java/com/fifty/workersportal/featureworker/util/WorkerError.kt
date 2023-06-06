package com.fifty.workersportal.featureworker.util

import com.fifty.workersportal.core.util.Error

sealed class WorkerError : Error() {
    object FieldEmpty : WorkerError()
    object InputTooShort : WorkerError()
    object InvalidEmail : WorkerError()
    object InvalidFirstName : WorkerError()
    object InvalidAge : WorkerError()
    object NoSkillSelected : WorkerError()
    object SkillWageError : WorkerError()
}