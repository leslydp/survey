package com.leslydp.surveylib.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun SingleChoiceQuestionCMP(
    options: List<String>,
    //questionState: MutableState<Boolean>,
    onAnswerSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var checkedState by remember { mutableStateOf(0) }
    //questionState.value = true

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

