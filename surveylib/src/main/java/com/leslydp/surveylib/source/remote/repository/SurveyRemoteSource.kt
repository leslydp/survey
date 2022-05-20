package com.leslydp.surveylib.source.remote.repository

import com.leslydp.surveylib.model.SurveyQuestion
import com.leslydp.surveylib.source.remote.api.SurveyApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal data class  SurveyRemoteSource(private val api: SurveyApi){
    suspend fun getSurvey(): SurveyQuestion? = withContext(Dispatchers.IO){
        api.fetchForm()
    }
}