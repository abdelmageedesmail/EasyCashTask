package com.abdelmageed.easycashtask.data.remote.responses.teams

data class Competition(
    val area: Area,
    val code: String,
    val id: Int,
    val lastUpdated: String,
    val name: String,
    val plan: String
)