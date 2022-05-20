package com.leslydp.surveylib.source.remote.api

import com.leslydp.surveylib.model.SurveyQuestion
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

internal class SurveyApi : KtorApi(){
    suspend fun fetchForm(): SurveyQuestion = client.get{
        apiUrl("load/form")
    }

    suspend fun  saveForm(answer: String): HttpResponse = client.post(){
        apiUrl("save/form")
        contentType(ContentType.Application.Json)
        body = answer
    }
}