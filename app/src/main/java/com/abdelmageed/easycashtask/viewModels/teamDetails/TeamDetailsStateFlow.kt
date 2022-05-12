package com.abdelmageed.easycashtask.viewModels.teamDetails

import com.abdelmageed.easycashtask.data.remote.responses.TeamDetailsResponse

sealed class TeamDetailsStateFlow {
    object loading : TeamDetailsStateFlow()
    data class success(val teamDetailsResponse: TeamDetailsResponse) : TeamDetailsStateFlow()
    data class error(val e: Throwable) : TeamDetailsStateFlow()
    object empty : TeamDetailsStateFlow()
}
