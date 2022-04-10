package com.leslydp.surveylib.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


sealed class SQuestion(val questionName: String) {
    class MultipleChoiceQuestion(questionName: String ,val options: List<String>) : SQuestion(questionName)
    class SingleChoiceQuestion(questionName: String, val options: List<String>) : SQuestion(questionName)
    class SliderQuestion(questionName: String, val options: List<String>): SQuestion(questionName)
    class TextQuestion(questionName: String): SQuestion(questionName)
}


/*@Stable
class QuestionState(
    val questionIndex: Int,
    val totalQuestionsCount: Int,
    val showPrevious: Boolean,
    val showDone: Boolean
) {
    var enableNext by mutableStateOf(false)
}

sealed class SurveyState {
    data class Questions(
        @StringRes val surveyTitle: Int,
        val questionsState: List<QuestionState>
    ) : SurveyState() {
        var currentQuestionIndex by mutableStateOf(0)
    }
}*/


/*enum class QUESTION{
    MultipleChoiceQuestion, SingleChoiceQuestion, SliderQuestion, TextQuestionCMP

}

data class Question(
    val type: Int,
    val questionName: String,
    var options: List<String>?=null
)*/

