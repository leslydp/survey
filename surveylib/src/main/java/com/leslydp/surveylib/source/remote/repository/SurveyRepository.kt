package com.leslydp.surveylib.source.remote.repository

import com.leslydp.surveylib.model.SurveyQuestion

class SurveyRepository internal constructor(private val remoteSource: SurveyRemoteSource){
    suspend fun getSurvey(): SurveyQuestion?{
        return remoteSource.getSurvey()
    }
}