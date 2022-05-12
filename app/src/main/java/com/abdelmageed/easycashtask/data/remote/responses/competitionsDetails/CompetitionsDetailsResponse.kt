package com.abdelmageed.easycashtask.data.remote.responses.competitionsDetails

data class CompetitionsDetailsResponse(
    val area: Area,
    val code: String,
    val currentSeason: CurrentSeason,
    val emblemUrl: String,
    val id: Int,
    val lastUpdated: String,
    val name: String,
    val plan: String,
    val message: String,
    val seasons: MutableList<Season>
)