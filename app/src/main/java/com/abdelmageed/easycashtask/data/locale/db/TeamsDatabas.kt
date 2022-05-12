package com.abdelmageed.easycashtask.data.locale.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abdelmageed.easycashtask.data.remote.responses.competitions.CompetionsResponse
import com.abdelmageed.easycashtask.data.remote.responses.competitionsDetails.CompetitionsDetailsResponse
import com.abdelmageed.easycashtask.data.remote.responses.teams.TeamsCompetitionsResponse

@Entity(tableName = "teams_table")
class TeamsDatabas(var teamsCompetitionsResponse: TeamsCompetitionsResponse) {

    @PrimaryKey(autoGenerate = false)
    var id: Int = 0

}