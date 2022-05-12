package com.abdelmageed.easycashtask.data.remote.responses

data class TeamDetailsResponse(
    val activeCompetitions: List<Any>,
    val address: String,
    val area: Area,
    val clubColors: String,
    val crestUrl: String,
    val email: String,
    val founded: Int,
    val id: Int,
    val lastUpdated: String,
    val name: String,
    val phone: String,
    val shortName: String,
    val squad: List<Any>,
    val tla: String,
    val venue: String,
    val website: String
)