package com.fifty.workersportal.featurelocation.domain.model

data class LocalAddress(
    val id: String,
    val title: String,
    val houseName: String,
    val place: String,
    val subLocality: String,
    val city: String,
    val state: String,
    val country: String,
    val pin: String,
    val location: List<Double>
)