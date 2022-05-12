package com.abdelmageed.easycashtask.data.locale.localeRepositries

import com.abdelmageed.easycashtask.data.locale.db.CompetitionsDao
import com.abdelmageed.easycashtask.data.locale.db.CompetitionsDatabas
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class LocaleCompetitionsStore @Inject constructor(val competitionsDao: CompetitionsDao) {

    suspend fun insertCompetitions(competitionsDatabas: CompetitionsDatabas) {
        competitionsDao.insertCompetitions(competitionsDatabas)
    }

    fun getCompetitions(): Flow<CompetitionsDatabas> {
        return competitionsDao.getCompetitions()
    }

}