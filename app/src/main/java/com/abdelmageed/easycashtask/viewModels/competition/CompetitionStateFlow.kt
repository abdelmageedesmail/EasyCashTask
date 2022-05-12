package com.abdelmageed.easycashtask.viewModels.competition

import com.abdelmageed.easycashtask.data.remote.responses.competitions.CompetionsResponse
import com.abdelmageed.easycashtask.data.remote.responses.competitions.Competition

sealed class CompetitionStateFlow {

    object loading : CompetitionStateFlow()
    data class success(val competitionResponse: CompetionsResponse) : CompetitionStateFlow()
    data class error(val e: Throwable) : CompetitionStateFlow()
    object empty : CompetitionStateFlow()

}
