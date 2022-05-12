package com.abdelmageed.easycashtask.data.remote.repositries

import com.abdelmageed.easycashtask.BuildConfig
import com.abdelmageed.easycashtask.data.remote.ApiInterface
import com.abdelmageed.easycashtask.data.remote.responses.competitions.CompetionsResponse
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CompetitionsRepositry @Inject constructor(val apiInterface: ApiInterface) {

    fun getCompetitions() = flow<CompetionsResponse> {
        emit(apiInterface.getCompetitions())
    }.flowOn(Dispatchers.IO)
}