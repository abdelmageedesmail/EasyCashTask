package com.abdelmageed.easycashtask.data.remote.responses.competitions

data class CompetionsResponse(
    val competitions: MutableList<Competition>,
    val count: Int,
    val message:String

)