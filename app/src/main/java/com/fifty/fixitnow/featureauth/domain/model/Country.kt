package com.fifty.fixitnow.featureauth.domain.model

data class Country(
    val alpha2Code: String,
    val callingCode: String,
    val flagUrl: String,
    val name: String
) {
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "$name$alpha2Code",
            alpha2Code,
            callingCode
        )
        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}