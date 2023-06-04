package com.fifty.workersportal.core.data.util

import com.fifty.workersportal.core.domain.model.User
import kotlin.properties.Delegates

object Session {

    lateinit var userId: String
    lateinit var userFirstName: String
    lateinit var userLastName: String
    var userIsWorker: Boolean? = null
}