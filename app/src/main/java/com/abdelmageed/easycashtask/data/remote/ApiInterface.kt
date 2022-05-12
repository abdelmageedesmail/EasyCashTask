package com.abdelmageed.easycashtask.data.remote

import com.abdelmageed.easycashtask.BuildConfig
import com.abdelmageed.easycashtask.data.remote.responses.TeamDetailsResponse
import com.abdelmageed.easycashtask.data.remote.responses.competitions.CompetionsResponse
import com.abdelmageed.easycashtask.data.remote.responses.competitionsDetails.CompetitionsDetailsResponse
import com.abdelmageed.easycashtask.data.remote.responses.teams.TeamsCompetitionsResponse
import retrofit2.http.*

interface ApiInterface {
    @GET("/v2/competitions")
//    @Header("X-Auth-Token") apiKey: String
    suspend fun getCompetitions(): CompetionsResponse

//    @Header("X-Auth-Token") apiKey: String

    @GET("/v2/competitions/{id}")
    suspend fun getCompetitionDetails(
        @Path("id") id: String
    ): CompetitionsDetailsResponse

    @GET("/v2/competitions/{id}/teams")
    suspend fun getTeams(
        @Path("id") id: String
    ): TeamsCompetitionsResponse

    @GET("/v2/teams/{id}")
    suspend fun getTeamDetails(@Path("id") id: String): TeamDetailsResponse
}