package com.abdelmageed.easycashtask.data.remote.responses.competitionsDetails

data class Season(
    val currentMatchday: Int,
    val endDate: String,
    val id: Int,
    val startDate: String,
    val winner: Winner
)