package com.fifty.fixitnow.core.util

object Constants {

    const val DEFAULT_PHONE_NUMBER_LENGTH = 10
    const val OTP_RESEND_INTERVAL = 20
    const val OTP_LENGTH = 6

    const val CURRENCY_SYMBOL = "₹"

    // Message defaults
    const val MESSAGE_LENGTH = 1000

    // Base urls
    const val REST_COUNTRIES_BASE_URL = "https://restcountries.com/v2/"

    const val WORKERS_PORTAL_BASE_URL = "http://192.168.1.36:8080/api/"
    const val DEBUG_WS_BASE_URL = "http://192.168.1.36:8080"
//    const val WORKERS_PORTAL_BASE_URL = "http://65.0.101.4/api/"
//    const val DEBUG_WS_BASE_URL = "http://65.0.101.4"

    const val RECONNECT_INTERVAL = 5000L

    // Country code default values.
    const val DEFAULT_COUNTRY_NAME = "India"
    const val DEFAULT_COUNTRY_CODE = "+91"
    const val DEFAULT_COUNTRY_FLAG_URL = "https://flagcdn.com/w320/in.png"

    // Worker
    const val MAX_SELECTED_SKILL_COUNT = 5
    const val MAXIMUM_SELECTABLE_CATEGORIES = 5
    const val MAXIMUM_NAME_LENGTH = 20
    const val MINIMUM_NAME_LENGTH = 3
    const val MAXIMUM_WORKER_AGE = 70
    const val MINIMUM_WORKER_AGE = 18

    // Address
    const val MINIMUM_ADDRESS_LENGTH = 10

    const val DEFAULT_PAGINATION_SIZE = 20
    const val SEARCH_DELAY = 700L

    val genderOptions = listOf(
        "Male", "Female", "Other"
    )
}