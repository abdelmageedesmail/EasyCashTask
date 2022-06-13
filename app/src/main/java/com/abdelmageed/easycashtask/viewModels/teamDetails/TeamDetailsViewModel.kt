package com.abdelmageed.easycashtask.viewModels.teamDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.abdelmageed.easycashtask.data.locale.db.CompetitionsDetailsDatabas
import com.abdelmageed.easycashtask.data.locale.db.TeamDetailsDatabas
import com.abdelmageed.easycashtask.data.locale.localeRepositries.LocaleTeamDetailsRepo
import com.abdelmageed.easycashtask.data.remote.repositries.TeamDetailsRepositry
import com.abdelmageed.easycashtask.data.remote.responses.TeamDetailsResponse
import com.abdelmageed.easycashtask.data.remote.responses.competitionsDetails.CompetitionsDetailsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamDetailsViewModel @Inject constructor(
    val teamDetailsRepositry: TeamDetailsRepositry,
    val localeTeamDetailsRepo: LocaleTeamDetailsRepo
) : ViewModel() {

    private val _mutableStateFlow =
        MutableStateFlow<TeamDetailsStateFlow>(TeamDetailsStateFlow.empty)

    val mutableStateFlow = _mutableStateFlow

    val readCashedTeamDetails: LiveData<TeamDetailsDatabas> =
        localeTeamDetailsRepo.getTeamDetails().asLiveData()

    fun getTeamDetails(id: String) {
        viewModelScope.launch {
            _mutableStateFlow.value = TeamDetailsStateFlow.loading
            teamDetailsRepositry.getTeamDetails(id).catch {
                _mutableStateFlow.value = TeamDetailsStateFlow.error(it.fillInStackTrace())
            }.collect {
                _mutableStateFlow.value = TeamDetailsStateFlow.success(it)
                cashCompetitionsDetails(it)
            }
        }
    }

    private fun insertTeamDetails(teamDetailsDatabas: TeamDetailsDatabas) {
        viewModelScope.launch(Dispatchers.IO) {
            localeTeamDetailsRepo.insertTeamDetails(teamDetailsDatabas)
        }
    }

    fun cashCompetitionsDetails(teamDetailsResponse: TeamDetailsResponse) {
        val recipesEntity = TeamDetailsDatabas(teamDetailsResponse)
        insertTeamDetails(recipesEntity)
    }

}