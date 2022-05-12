package com.abdelmageed.easycashtask.data.locale.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abdelmageed.easycashtask.data.remote.responses.TeamDetailsResponse
import com.abdelmageed.easycashtask.data.remote.responses.competitions.CompetionsResponse

@Entity(tableName = "team_details_table")
class TeamDetailsDatabas(var teamDetailsResponse: TeamDetailsResponse) {

    @PrimaryKey(autoGenerate = false)
    var id: Int = 0

}