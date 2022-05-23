package com.leslydp.surveylib

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.*
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
    onBackPressed: () -> Unit,
    onDonePressed: () -> Unit,
    onAnswer: (Answer) -> Unit,
    surveyViewModel: SurveyViewModel = hiltViewModel()
) {
    val questionindex = remember{ mutableStateOf(0)}
    val sendInfo = remember {
        mutableStateOf(false)
    }
    var ans = StringBuilder()
    val questionState = remember{ mutableStateOf(false)}
    val questions = surveyViewModel.surveyQuestion.collectAsState()
    if(questions.value!= null) {
        questions.value?.form!!.forEachIndexed { index, form ->
            if(index < questions.value?.form!!.size -1)
                ans.append(" [!]")
            else
                ans.append(" ")
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
                        questionIndex = questionindex.value,
                        totalQuestionsCount = questions.value?.form!!.size,
                        onBackPressed = onBackPressed
                    )
                },
                content = {
                    JetSurveyScreen(
                        questions.value!!.form[questionindex.value],
                        questionState,
                        onAnswer = { respuesta ->
                            var split = ans.split("[!]").toMutableList()
                            split[questionindex.value] = respuesta.toString()
                            ans.clear()
                            split.forEachIndexed { index, questionState ->
                                if(index < questions.value?.form!!.size -1)
                                    ans.append(" [!]")
                                else
                                    ans.append(" ")
                            }

                        })
                    Log.d("respuesta3", ans.toString())
                },
                bottomBar = {
                    SurveyBottomBar(
                        questionIndex = questionindex.value,
                        questionSize = questions.value?.form!!.size,
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


















