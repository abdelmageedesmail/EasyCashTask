package com.abdelmageed.easycashtask.data.locale.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abdelmageed.easycashtask.data.remote.responses.competitions.CompetionsResponse
import com.abdelmageed.easycashtask.data.remote.responses.competitionsDetails.CompetitionsDetailsResponse

@Entity(tableName = "competitions_details_table")
class CompetitionsDetailsDatabas(var competitionsDetailsResponse: CompetitionsDetailsResponse) {

    @PrimaryKey(autoGenerate = false)
    var id: Int = 0

}