package com.abdelmageed.easycashtask.viewModels.competitionDetails

import com.abdelmageed.easycashtask.data.remote.responses.competitions.CompetionsResponse
import com.abdelmageed.easycashtask.data.remote.responses.competitions.Competition
import com.abdelmageed.easycashtask.data.remote.responses.competitionsDetails.CompetitionsDetailsResponse
import com.abdelmageed.easycashtask.data.remote.responses.teams.TeamsCompetitionsResponse

sealed class CompetitionDetailsStateFlow {

    object loading : CompetitionDetailsStateFlow()
    data class success(val competitionsDetailsResponse: CompetitionsDetailsResponse) :
        CompetitionDetailsStateFlow()

    data class successTeams(val teamsCompetitionsResponse: TeamsCompetitionsResponse) :
        CompetitionDetailsStateFlow()

    data class error(val e: Throwable) : CompetitionDetailsStateFlow()
    object empty : CompetitionDetailsStateFlow()

}
