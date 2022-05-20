package com.leslydp.surveylib.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable
data class SurveyQuestion(
    val form: List<Form>
)