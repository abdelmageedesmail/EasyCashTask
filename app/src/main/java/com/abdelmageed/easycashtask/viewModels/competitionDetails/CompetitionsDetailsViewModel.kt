package com.abdelmageed.easycashtask.viewModels.competitionDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.abdelmageed.easycashtask.data.locale.db.CompetitionsDatabas
import com.abdelmageed.easycashtask.data.locale.db.CompetitionsDetailsDatabas
import com.abdelmageed.easycashtask.data.locale.db.TeamsDatabas
import com.abdelmageed.easycashtask.data.locale.localeRepositries.LocaleCompetitionsDetailsStore
import com.abdelmageed.easycashtask.data.remote.repositries.CompetitionDetailsRepositery
import com.abdelmageed.easycashtask.data.remote.responses.competitions.CompetionsResponse
import com.abdelmageed.easycashtask.data.remote.responses.competitionsDetails.CompetitionsDetailsResponse
import com.abdelmageed.easycashtask.data.remote.responses.teams.TeamsCompetitionsResponse
import com.abdelmageed.easycashtask.viewModels.competition.CompetitionStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CompetitionsDetailsViewModel @Inject constructor(
    val repositery: CompetitionDetailsRepositery,
    val localeCompetitionsDetailsStore: LocaleCompetitionsDetailsStore

) : ViewModel() {

    private val _mutableStateFlow =
        MutableStateFlow<CompetitionDetailsStateFlow>(CompetitionDetailsStateFlow.empty)

    val mutableStateFlow = _mutableStateFlow

    val readCashedCompetitions: LiveData<CompetitionsDetailsDatabas> =
        localeCompetitionsDetailsStore.getCompetitions().asLiveData()

    val readCashedTeams: LiveData<TeamsDatabas> =
        localeCompetitionsDetailsStore.getTeams().asLiveData()

    suspend fun getCompetitionsDetails(id: String) {
        viewModelScope.launch {
            async {
                _mutableStateFlow.value = CompetitionDetailsStateFlow.loading

                repositery.getCompetitionsDetails(id).catch {
                    _mutableStateFlow.value =
                        CompetitionDetailsStateFlow.error(it.fillInStackTrace())
                }.collect {
                    _mutableStateFlow.value = CompetitionDetailsStateFlow.success(it)
                }
            }
            async {
                viewModelScope.launch {
                    _mutableStateFlow.value = CompetitionDetailsStateFlow.loading
                    repositery.getTeams(id).catch {
                        _mutableStateFlow.value =
                            CompetitionDetailsStateFlow.error(it.fillInStackTrace())
                    }.collect {
                        _mutableStateFlow.value = CompetitionDetailsStateFlow.successTeams(it)
                    }
                }
            }
        }

    }

    private fun insertCompetitionsDetails(competitionsDatabas: CompetitionsDetailsDatabas) {
        viewModelScope.launch(Dispatchers.IO) {
            localeCompetitionsDetailsStore.insertCompetitionsDetails(competitionsDatabas)
        }
    }

    fun cashCompetitionsDetails(competionsResponse: CompetitionsDetailsResponse) {
        val recipesEntity = CompetitionsDetailsDatabas(competionsResponse)
        insertCompetitionsDetails(recipesEntity)
    }

    private fun insertTeams(teamsDatabas: TeamsDatabas) {
        viewModelScope.launch(Dispatchers.IO) {
            localeCompetitionsDetailsStore.insertTeams(teamsDatabas)
        }
    }

    fun cashTeams(teamsCompetitionsResponse: TeamsCompetitionsResponse) {
        val teamsEntity = TeamsDatabas(teamsCompetitionsResponse)
        insertTeams(teamsEntity)
    }
}