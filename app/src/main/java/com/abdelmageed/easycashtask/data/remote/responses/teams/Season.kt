package com.abdelmageed.easycashtask.data.remote.responses.teams

data class Season(
    val currentMatchday: Int,
    val endDate: String,
    val id: Int,
    val startDate: String,
    val winner: Any
)