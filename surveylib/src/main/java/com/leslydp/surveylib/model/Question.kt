package com.leslydp.surveylib.model

data class Question(
    val id: Int,
    val questionName: String,
    val options: List<String>
)

