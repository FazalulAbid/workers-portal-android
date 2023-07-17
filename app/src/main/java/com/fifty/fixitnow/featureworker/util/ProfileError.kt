package com.fifty.fixitnow.featureworker.util

import com.fifty.fixitnow.core.util.Error

sealed class ProfileError : Error() {
    object FieldEmpty : ProfileError()
    object InputTooShort : ProfileError()
    object InvalidEmail : ProfileError()
    object InvalidFirstName : ProfileError()
    object InvalidAge : ProfileError()
    object InvalidBio : ProfileError()
    object NoSkillSelected : ProfileError()
    object NoPrimarySkillSelected : ProfileError()
    object SkillWageError : ProfileError()
}