package com.leslydp.surveylib.model


sealed class SQuestion(val questionName: String) {
    class MultipleChoiceQuestion(val options: List<String>, questionName: String) : SQuestion(questionName)
    class SingleChoiceQuestion(questionName: String, val options: List<String>) : SQuestion(questionName)
    class SliderQuestion(questionName: String, val options: List<String>): SQuestion(questionName)
    class TextQuestion(questionName: String): SQuestion(questionName)
}



/*enum class QUESTION{
    MultipleChoiceQuestion, SingleChoiceQuestion, SliderQuestion, TextQuestionCMP

}

data class Question(
    val type: Int,
    val questionName: String,
    var options: List<String>?=null
)*/

