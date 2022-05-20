package com.leslydp.surveylib.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leslydp.surveylib.model.SurveyQuestion
import com.leslydp.surveylib.source.remote.repository.SurveyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurveyViewModel @Inject constructor(private val surveyRepository: SurveyRepository): ViewModel(){
    private val _surveyQuestion = MutableStateFlow<SurveyQuestion?>(null)
    val surveyQuestion = _surveyQuestion
    init {
        getForm()
    }

    fun getForm(){
        viewModelScope.launch(Dispatchers.IO) {
            _surveyQuestion.value = surveyRepository.getSurvey()
        }
    }
}