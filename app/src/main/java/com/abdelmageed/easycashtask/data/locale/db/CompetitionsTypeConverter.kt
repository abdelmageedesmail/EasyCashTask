package com.abdelmageed.easycashtask.data.locale.db

import androidx.room.TypeConverter
import com.abdelmageed.easycashtask.data.remote.responses.TeamDetailsResponse
import com.abdelmageed.easycashtask.data.remote.responses.competitions.CompetionsResponse
import com.abdelmageed.easycashtask.data.remote.responses.competitionsDetails.CompetitionsDetailsResponse
import com.abdelmageed.easycashtask.data.remote.responses.teams.TeamsCompetitionsResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CompetitionsTypeConverter {
    val gson = Gson()

    @TypeConverter
    fun recipeToString(competionsResponse: CompetionsResponse): String {
        return gson.toJson(competionsResponse)
    }

    @TypeConverter
    fun stringToRecipe(recipeString: String): CompetionsResponse {
        val objectType = object : TypeToken<CompetionsResponse>() {}.type
        return gson.fromJson(recipeString, objectType)
    }

    @TypeConverter
    fun competitionsDetailsToString(competitionsDetailsResponse: CompetitionsDetailsResponse): String {
        return gson.toJson(competitionsDetailsResponse)
    }

    @TypeConverter
    fun stringTocompetitionsDetails(recipeString: String): CompetitionsDetailsResponse {
        val objectType = object : TypeToken<CompetitionsDetailsResponse>() {}.type
        return gson.fromJson(recipeString, objectType)
    }

    @TypeConverter
    fun teamsToString(teamsCompetitionsResponse: TeamsCompetitionsResponse): String {
        return gson.toJson(teamsCompetitionsResponse)
    }

    @TypeConverter
    fun stringToTeams(recipeString: String): TeamsCompetitionsResponse {
        val objectType = object : TypeToken<TeamsCompetitionsResponse>() {}.type
        return gson.fromJson(recipeString, objectType)
    }

    @TypeConverter
    fun teamDetailsToString(teamDetailsResponse: TeamDetailsResponse): String {
        return gson.toJson(teamDetailsResponse)
    }

    @TypeConverter
    fun stringToTeamDetails(recipeString: String): TeamDetailsResponse {
        val objectType = object : TypeToken<TeamDetailsResponse>() {}.type
        return gson.fromJson(recipeString, objectType)
    }
}