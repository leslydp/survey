package com.leslydp.surveylib

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.leslydp.surveylib.components.SurveyBottomBar
import com.leslydp.surveylib.components.SurveyTopAppBar
import com.leslydp.surveylib.model.Answer
import com.leslydp.surveylib.model.SQuestion
import com.leslydp.surveylib.screens.*



@Composable
fun SurveyQuestionsScreen(
    questionsList: List<SQuestion>,
    onBackPressed: () -> Unit,
    onDonePressed: () -> Unit,
    onAnswer: (Answer) -> Unit
) {
    val questionindex = remember{ mutableStateOf(0)}
    val sendInfo = remember {
        mutableStateOf(false)
    }
    val ans =
        mutableListOf<String>()
    var questionState = remember{ mutableStateOf(false)}

    Surface(modifier = Modifier
        .fillMaxWidth()
        .wrapContentWidth(align = Alignment.CenterHorizontally)
        .widthIn(max = 840.dp)) {
        Scaffold(
            topBar = {
                SurveyTopAppBar(
                    questionIndex = questionindex.value,
                    totalQuestionsCount = questionsList.size,
                    onBackPressed = onBackPressed
                )
            },
            content = { innerPadding ->
                JetSurveyScreen(questionsList[questionindex.value],questionState, onAnswer = {ans.add(it)})
                Log.d("respuesta3", ans.toString())
            },
            bottomBar = {
                SurveyBottomBar(
                    questionIndex = questionindex.value,
                    questionSize = questionsList.size,
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
        if(sendInfo.value){
            onAnswer(Answer((ans)))
            Log.d("Respuesta", ans.toString())
        }else{
            Log.d("Respuesta", "NOT SENDING INFO")
        }

    }


}


















