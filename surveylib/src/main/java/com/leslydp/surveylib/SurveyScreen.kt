package com.leslydp.surveylib

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.leslydp.surveylib.model.Answer
import com.leslydp.surveylib.model.SQuestion


@OptIn(
    ExperimentalMaterialApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class,
    androidx.compose.animation.ExperimentalAnimationApi::class
)
@Composable
fun JetsurveyScreen(
    list: List<SQuestion>,
    onAnswer: (Answer) -> Unit
) {
    //var checkedState by remember{mutableStateOf(false)}

    list.forEach { question ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp)
        ) {
            var ans = mutableListOf<String>()
            item {
                Spacer(modifier = Modifier.height(32.dp))
                QuestionTitle(question.questionName)
                Spacer(modifier = Modifier.height(24.dp))

                Log.d("myTag2", question.questionName)
                //QuestionTitle(question.questionName)
                //Log.d("tag", question.options.size.toString())
                when (question) {
                    is SQuestion.MultipleChoiceQuestion -> {
                        val respuesta = mutableListOf<Byte>()

                        MultipleChoiceQuestionCMP(question.options, onAnswerSelected = { idq, valor ->
                            val compareTo = valor.compareTo(false)
                            respuesta[idq] = compareTo.toByte()
                            Log.d("myTag", respuesta.toString())

                        })
                        ans.add(respuesta.toString())

                    }
                    is SQuestion.SingleChoiceQuestion -> {
                        val respuesta = mutableListOf<Byte>()

                        SingleChoiceQuestionCMP(question.options, onAnswerSelected = { id ->
                            if (respuesta.indexOf(1) > -1)
                                respuesta[respuesta.indexOf(1)] = 0
                            respuesta[id] = 1
                            Log.d("myTag", respuesta.toString())

                        })
                        ans.add(respuesta.toString())

                    }
                    is SQuestion.SliderQuestion -> {
                        var respuesta = 0f
                        SliderQuestionCMP(question.options, onAnswerSelected = {
                            Log.d("slider", it.toString())
                            respuesta = it

                        })
                        ans.add("[!]${respuesta}")
                    }

                    is SQuestion.TextQuestion-> {
                        var respuesta=""
                        TextQuestionCMP(onAnswerWritten = {
                            Log.d("text", it)
                        })
                        ans.add(respuesta)
                    }


                }
            }
        }
    }
}

@Composable
private fun QuestionTitle(title: String) {
    val backgroundColor = if (MaterialTheme.colors.isLight) {
        MaterialTheme.colors.onSurface.copy(alpha = 0.04f)
    } else {
        MaterialTheme.colors.onSurface.copy(alpha = 0.06f)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = backgroundColor,
                shape = MaterialTheme.shapes.small
            )
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = 16.dp)
        )
    }
}

@Composable
private fun MultipleChoiceQuestionCMP(

    options: List<String>,
    onAnswerSelected: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {
        for (option in options) {
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

@Composable
private fun SingleChoiceQuestionCMP(
    options: List<String>,
    onAnswerSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var checkedState by remember { mutableStateOf(-1) }

    Column(modifier = modifier) {
        options.forEach { text ->

            val optionSelected = options.indexOf(text) == checkedState

            val answerBorderColor = if (optionSelected) {
                MaterialTheme.colors.primary.copy(alpha = 0.5f)
            } else {
                MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
            }
            val answerBackgroundColor = if (optionSelected) {
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
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = options.indexOf(text) == checkedState,
                            onClick = {
                                checkedState = options.indexOf(text)
                                onAnswerSelected(options.indexOf(text))
                            }
                        )
                        .background(answerBackgroundColor)
                        .padding(vertical = 16.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = text
                    )

                    RadioButton(
                        selected = options.indexOf(text) == checkedState,
                        onClick = {
                            checkedState = options.indexOf(text)
                            onAnswerSelected(options.indexOf(text))
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = MaterialTheme.colors.primary
                        )
                    )
                }
            }
        }
    }
}


@Composable
private fun SliderQuestionCMP(
    //possibleAnswer: PossibleAnswer.Slider,
    //answer: Answer.Slider?,
    options: List<String>,
    onAnswerSelected: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    var sliderPosition by remember {
        mutableStateOf(0f)
    }
    Row(modifier = modifier) {

        Slider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
                onAnswerSelected(it)
            },
            valueRange = 0f..100f,
            steps = options.size,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        )
    }
    Row {
        for (option in options)
            Text(
                text = option,
                style = MaterialTheme.typography.caption,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.8f)
            )

    }
}

@Composable
fun TextQuestionCMP(
    //options: List<String>,
    onAnswerWritten: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    //val (textState, setTextState) = remember { mutableStateOf("") }
    val textState = remember { mutableStateOf(TextFieldValue()) }
    TextField(modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        .clip(RoundedCornerShape(10.dp))
        .border(2.dp, color = Color.Gray),
        placeholder = { Text("Some description") },
        value = textState.value,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = White,
            cursorColor = Color.Black,
            disabledLabelColor = White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        //shape = RoundedCornerShape(100.dp),
        onValueChange = {
            textState.value = it
            onAnswerWritten(textState.value.text)
        })



}