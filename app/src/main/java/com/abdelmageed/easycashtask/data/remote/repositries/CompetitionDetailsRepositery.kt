package com.abdelmageed.easycashtask.data.remote.repositries

import com.abdelmageed.easycashtask.BuildConfig
import com.abdelmageed.easycashtask.data.remote.ApiInterface
import com.abdelmageed.easycashtask.data.remote.responses.competitionsDetails.CompetitionsDetailsResponse
import com.abdelmageed.easycashtask.data.remote.responses.teams.TeamsCompetitionsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CompetitionDetailsRepositery @Inject constructor(val apiInterface: ApiInterface) {

    fun getCompetitionsDetails(id: String) = flow<CompetitionsDetailsResponse> {
        emit(apiInterface.getCompetitionDetails(id))
    }.flowOn(Dispatchers.IO)


    fun getTeams(id: String) = flow<TeamsCompetitionsResponse> {
        emit(apiInterface.getTeams(id))
    }.flowOn(Dispatchers.IO)

}