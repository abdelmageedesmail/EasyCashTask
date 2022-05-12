package com.abdelmageed.easycashtask.data.locale.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abdelmageed.easycashtask.data.remote.responses.competitionsDetails.CompetitionsDetailsResponse
import kotlinx.coroutines.flow.Flow


@Dao
interface CompetitionsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCompetitions(competitionsDatabas: CompetitionsDatabas)

    @Query("SELECT * FROM competitions_table ORDER BY id ASC")
    fun getCompetitions(): Flow<CompetitionsDatabas>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCompetitionsDetails(competitionsDetailsDatabas: CompetitionsDetailsDatabas)

    @Query("SELECT * FROM competitions_details_table ORDER BY id ASC")
    fun getCompetitionsDetails(): Flow<CompetitionsDetailsDatabas>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTeams(teamsDatabas: TeamsDatabas)

    @Query("SELECT * FROM teams_table ORDER BY id ASC")
    fun getTeams(): Flow<TeamsDatabas>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTeamDetails(teamDetailsDatabas: TeamDetailsDatabas)

    @Query("SELECT * FROM team_details_table ORDER BY id ASC")
    fun getTeamDetails(): Flow<TeamDetailsDatabas>

}