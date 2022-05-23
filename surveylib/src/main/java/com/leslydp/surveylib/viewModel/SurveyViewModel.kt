package com.leslydp.surveylib.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leslydp.surveylib.model.QuestionState
import com.leslydp.surveylib.model.SurveyQuestion
import com.leslydp.surveylib.model.SurveyState
import com.leslydp.surveylib.source.remote.repository.SurveyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurveyViewModel @Inject constructor(private val surveyRepository: SurveyRepository): ViewModel(){
    private val _surveyQuestion = MutableStateFlow<SurveyQuestion?>(null)
    private lateinit var surveyInitialState: SurveyState.Questions
    private val _uiState = MutableLiveData<SurveyState.Questions>()
    var uiState: LiveData<SurveyState.Questions>? = null
        get() = _uiState
    val surveyQuestion = _surveyQuestion
    init {
        getForm()
        viewModelScope.launch(Dispatchers.IO) {
            if (surveyQuestion.value != null) {
                val questions: List<QuestionState> =
                    surveyQuestion.value?.form!!.mapIndexed { index, question ->
                        val showPrevious = index > 0
                        val showDone = index == surveyQuestion.value?.form!!.size - 1
                        QuestionState(
                            question = question,
                            questionIndex = index,
                            totalQuestionsCount = surveyQuestion.value?.form!!.size,
                            showPrevious = showPrevious,
                            showDone = showDone
                        )
                    }
                surveyInitialState =
                    SurveyState.Questions(surveyQuestion.value?.form!!.size, questions)
                _uiState.value = surveyInitialState
                uiState = _uiState
            }
            else{
               Log.d("it","fjdfsj")

            }
        }

    }

    fun getForm(){
        viewModelScope.launch(Dispatchers.IO) {
            _surveyQuestion.value = surveyRepository.getSurvey()
        }
    }
}