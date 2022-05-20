package com.leslydp.surveylib.model

import kotlinx.serialization.Serializable

@Serializable
data class Form(
    val options: List<String>,
    val question: String,
    val type: Int
)