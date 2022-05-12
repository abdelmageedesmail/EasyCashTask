package com.abdelmageed.easycashtask.data.locale.localeRepositries

import com.abdelmageed.easycashtask.data.locale.db.CompetitionsDao
import com.abdelmageed.easycashtask.data.locale.db.CompetitionsDatabas
import com.abdelmageed.easycashtask.data.locale.db.CompetitionsDetailsDatabas
import com.abdelmageed.easycashtask.data.locale.db.TeamsDatabas
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class LocaleCompetitionsDetailsStore @Inject constructor(val competitionsDao: CompetitionsDao) {

    suspend fun insertCompetitionsDetails(competitionsDetailsDatabas: CompetitionsDetailsDatabas) {
        competitionsDao.insertCompetitionsDetails(competitionsDetailsDatabas)
    }

    fun getCompetitions(): Flow<CompetitionsDetailsDatabas> {
        return competitionsDao.getCompetitionsDetails()
    }

    suspend fun insertTeams(teamsDatabas: TeamsDatabas) {
        competitionsDao.insertTeams(teamsDatabas)
    }

    fun getTeams(): Flow<TeamsDatabas> {
        return competitionsDao.getTeams()
    }

}