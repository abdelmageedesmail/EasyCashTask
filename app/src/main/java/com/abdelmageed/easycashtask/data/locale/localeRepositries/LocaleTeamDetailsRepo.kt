package com.abdelmageed.easycashtask.data.locale.localeRepositries

import com.abdelmageed.easycashtask.data.locale.db.CompetitionsDao
import com.abdelmageed.easycashtask.data.locale.db.CompetitionsDatabas
import com.abdelmageed.easycashtask.data.locale.db.TeamDetailsDatabas
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocaleTeamDetailsRepo @Inject constructor(val competitionsDao: CompetitionsDao) {

    suspend fun insertTeamDetails(teamDetailsDatabas: TeamDetailsDatabas) {
        competitionsDao.insertTeamDetails(teamDetailsDatabas)
    }

    fun getTeamDetails(): Flow<TeamDetailsDatabas> {
        return competitionsDao.getTeamDetails()
    }

}