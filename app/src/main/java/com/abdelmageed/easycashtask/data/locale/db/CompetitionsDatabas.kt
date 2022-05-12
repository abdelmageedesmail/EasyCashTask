package com.abdelmageed.easycashtask.data.locale.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abdelmageed.easycashtask.data.remote.responses.competitions.CompetionsResponse

@Entity(tableName = "competitions_table")
class CompetitionsDatabas(var competionsResponse: CompetionsResponse) {

    @PrimaryKey(autoGenerate = false)
    var id: Int = 0

}