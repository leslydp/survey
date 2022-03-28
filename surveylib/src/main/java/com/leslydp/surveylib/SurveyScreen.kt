package com.leslydp.surveylib

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.leslydp.surveylib.model.Answer
import com.leslydp.surveylib.model.Question

@OptIn(
    ExperimentalMaterialApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class,
    androidx.compose.animation.ExperimentalAnimationApi::class
)
@Composable
fun JetsurveyScreen(
    list: List<Question>,
    onAnswer: (Answer) -> Unit
) {
    //var checkedState by remember{mutableStateOf(false)}

    list.forEach { question ->
        var respuesta: ArrayList<Byte> = ArrayList<Byte>()
        question.options.forEach {
            respuesta.add(0)
        }
        Log.d("tag", question.options.size.toString())
        when (question.id) {
            1 -> {
                MultipleChoiceQuestion(question.options, onAnswerSelected = { idq, valor ->
                    val compareTo = valor.compareTo(false)
                    respuesta.set(idq, compareTo.toByte())
                    Log.d("myTag", respuesta.toString())

                })
            }
        }
    }

}

@Composable
private fun MultipleChoiceQuestion(
    //possibleAnswer: PossibleAnswer.MultipleChoice,
    //answer: Answer.MultipleChoice?,
    options: List<String>,
    onAnswerSelected: (Int, Boolean) -> Unit,
    //onAnswerSelected: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    //var checkedState by remember{mutableStateOf(false)}
    //val (value, setValue) = remember { mutableStateOf(Boolean[]) }
    //val options = possibleAnswer.optionsStringRes.associateBy { stringResource(id = it) }
    Column(modifier = modifier) {
        for (option in options) {
            // } //{
            // val selectedOption = answer?.answersStringRes?.contains(option.value)
            // val selectedOption = options.indexOf(option)
            // mutableStateOf(selectedOption ?: false)
            // }
            var checkedState by remember { mutableStateOf(false) }
            val answerBorderColor = if (checkedState) {
                MaterialTheme.colors.primary.copy(alpha = 0.5f)
            } else {
                MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
            }
            val answerBackgroundColor = if (checkedState) {
                MaterialTheme.colors.primary.copy(alpha = 0.12f)
            } else {
                MaterialTheme.colors.background
            }
            Surface(
                shape = MaterialTheme.shapes.small,
                border = BorderStroke(
                    width = 1.dp,
                    color = answerBorderColor
                ),
                modifier = Modifier
                    .padding(vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(answerBackgroundColor)
                        .clickable(
                            onClick = {
                                checkedState = !checkedState
                                onAnswerSelected(options.indexOf(option), checkedState)
                                //onAnswerSelected(checkedState)
                            }
                        )
                        .padding(vertical = 16.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = option)

                    Checkbox(
                        checked = checkedState,
                        onCheckedChange = { selected ->
                            checkedState = selected
                            onAnswerSelected(options.indexOf(option), selected)
                            //onAnswerSelected(checkedState)
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colors.primary
                        ),
                    )
                }
            }
        }
    }
}
