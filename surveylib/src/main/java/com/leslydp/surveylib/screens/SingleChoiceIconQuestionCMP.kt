package com.leslydp.surveylib.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.*
 import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.leslydp.surveylib.R
import com.ondev.imageblurkt_lib.ImageBlur

@Composable
internal fun SingleChoiceIconQuestionCMP(
    options: List<String>,
    //questionState: MutableState<Boolean>,
    url: List<String>,
    blurhash: List<String>,
    onAnswerSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var checkedState by remember { mutableStateOf(-1) }
    //questionState.value = true
    Column(modifier = modifier) {
        options.forEach { option ->

            val optionSelected = options.indexOf(option) == checkedState

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
                            selected = options.indexOf(option) == checkedState,
                            onClick = {
                                checkedState = options.indexOf(option)
                                onAnswerSelected(options.indexOf(option))
                            }
                        )
                        .background(answerBackgroundColor)
                        .padding(vertical = 16.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val resources = LocalContext.current.resources
                    ImageBlur(
                        modifier = Modifier
                            .width(56.dp)
                            .height(56.dp)
                            .clip(MaterialTheme.shapes.medium),
                        blurhash = blurhash[options.indexOf(option)],
                        imageUrl = url[options.indexOf(option)],
                        notImageFoundRes = R.drawable.ic_no_image, // Esta es una imagen que se pone en la carpeta drawable q se mostrara en caso de que no exista la imagena ala hora de cargarla
                        resources = resources,
                    )
                    Text(
                        text = option
                    )

                    RadioButton(
                        selected = options.indexOf(option) == checkedState,
                        onClick = {
                            checkedState = options.indexOf(option)
                            onAnswerSelected(options.indexOf(option))
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