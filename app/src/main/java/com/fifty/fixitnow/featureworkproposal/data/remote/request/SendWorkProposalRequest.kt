package com.fifty.fixitnow.featureworkproposal.data.remote.request

data class SendWorkProposalRequest(
    val workerId: String,
    val chosenCategoryId: String,
    val wage: Float,
    val isFullDay: Boolean,
    val isBeforeNoon: Boolean,
    val proposedDate: Long,
    val workDescription: String,
    val proposedAddressId: String
)
