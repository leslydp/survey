package com.leslydp.surveylib

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.leslydp.surveylib.components.SurveyBottomBar
import com.leslydp.surveylib.components.SurveyTopAppBar
import com.leslydp.surveylib.model.Answer
import com.leslydp.surveylib.screens.JetSurveyScreen
import com.leslydp.surveylib.viewModel.SurveyViewModel


@Composable
fun SurveyQuestionsScreen(
    //questions: SurveyState.Questions,
    onBackPressed: () -> Unit,
    onDonePressed: () -> Unit,
    onAnswer: (Answer) -> Unit,
    surveyViewModel: SurveyViewModel = hiltViewModel()
) {
    val questions = surveyViewModel.uiState?.value

    val questionindex = remember{ mutableStateOf(0)}
    val sendInfo = remember {
        mutableStateOf(false)
    }
    var ans = StringBuilder()



    //val questionState = remember{ mutableStateOf(false)}
    //val questions = surveyViewModel.surveyQuestion.collectAsState()
    if(questions!= null) {
        Log.d("null","Hola")

        questions.questionsState.forEachIndexed { index, questionState ->
            if(index < questions.questionsState.size-1)
                ans.append(" [!]")
            else
                ans.append(" ")
        }
        val questionState = remember(questions.currentQuestionIndex) {
            questions.questionsState[questions.currentQuestionIndex]
        }
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .widthIn(max = 840.dp)
        ) {
            Scaffold(
                topBar = {
                    SurveyTopAppBar(
                        questionIndex = questionState.questionIndex,
                        totalQuestionsCount = questionState.totalQuestionsCount,
                        onBackPressed = onBackPressed
                    )
                },
                content = {
                    JetSurveyScreen(
                        questionState.question,
                       // questionState,
                        onAnswer = {respuesta ->
                            var split = ans.split("[!]").toMutableList()
                            split[questionState.questionIndex] = respuesta.toString()
                            ans.clear()
                            split.forEachIndexed { index, questionState ->
                                if(index < questions.questionsState.size-1)
                                    ans.append(" [!]")
                                else
                                    ans.append(" ")
                            }

                        }
                    )
                    Log.d("respuesta3", ans.toString())
                },
                bottomBar = {
                    SurveyBottomBar(
                        questionIndex = questionState.questionIndex,
                        questionSize = questionState.totalQuestionsCount,
                        onPreviousPressed = { questionindex.value -= 1 },
                        onNextPressed = { questionindex.value += 1 },
                        onDonePressed = {
                            onDonePressed()
                            sendInfo.value = true
                        },
                        questionState = questionState
                    )
                }
            )
        }
        LaunchedEffect(sendInfo.value) {
            if (sendInfo.value) {
                //onAnswer(Answer((ans)))
                Log.d("Respuesta", ans.toString())
            } else {
                Log.d("Respuesta", "NOT SENDING INFO")
            }

        }

    }
    else{
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            CircularProgressIndicator()
        }

    }
}


















