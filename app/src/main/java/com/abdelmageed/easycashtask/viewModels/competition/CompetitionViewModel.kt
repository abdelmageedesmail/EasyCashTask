package com.abdelmageed.easycashtask.viewModels.competition

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.abdelmageed.easycashtask.data.locale.db.CompetitionsDatabas
import com.abdelmageed.easycashtask.data.locale.localeRepositries.LocaleCompetitionsStore
import com.abdelmageed.easycashtask.data.remote.repositries.CompetitionsRepositry
import com.abdelmageed.easycashtask.data.remote.responses.competitions.CompetionsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class CompetitionViewModel @Inject constructor(
    val competitionsRepositry: CompetitionsRepositry,
    val localeCompetitionsStore: LocaleCompetitionsStore
) :
    ViewModel() {

    private var _mutableStateFlow =
        MutableStateFlow<CompetitionStateFlow>(CompetitionStateFlow.empty)

    var mutableStateFlow = _mutableStateFlow

    val readCashedCompetitions: LiveData<CompetitionsDatabas> =
        localeCompetitionsStore.getCompetitions().asLiveData()


    fun getCompetitions() {
        viewModelScope.launch {
            _mutableStateFlow.value = CompetitionStateFlow.loading
            competitionsRepositry.getCompetitions().catch {
                _mutableStateFlow.value = CompetitionStateFlow.error(it.fillInStackTrace())
            }.collect {
                _mutableStateFlow.value = CompetitionStateFlow.success(it)
            }
        }
    }

    private fun insertCompetitions(competitionsDatabas: CompetitionsDatabas) {
        viewModelScope.launch(Dispatchers.IO) {
            localeCompetitionsStore.insertCompetitions(competitionsDatabas)
        }
    }

    fun cashCompetitions(competionsResponse: CompetionsResponse) {
        val recipesEntity = CompetitionsDatabas(competionsResponse)
        insertCompetitions(recipesEntity)
    }
}