package com.abdelmageed.easycashtask.data.remote.responses.teams

data class TeamsCompetitionsResponse(
    val competition: Competition,
    val count: Int,
    val filters: Filters,
    val season: Season,
    val teams: MutableList<Team>
)
