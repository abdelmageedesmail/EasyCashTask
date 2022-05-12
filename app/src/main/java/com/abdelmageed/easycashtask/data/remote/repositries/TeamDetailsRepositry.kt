package com.abdelmageed.easycashtask.data.remote.repositries

import com.abdelmageed.easycashtask.data.remote.ApiInterface
import com.abdelmageed.easycashtask.data.remote.responses.TeamDetailsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TeamDetailsRepositry @Inject constructor(val apiInterface: ApiInterface) {

    fun getTeamDetails(id: String) = flow<TeamDetailsResponse> {
        emit(apiInterface.getTeamDetails(id))
    }.flowOn(Dispatchers.IO)

}