package com.leslydp.surveylib.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.leslydp.surveylib.R
import com.ondev.imageblurkt_lib.ImageBlur

@OptIn(ExperimentalCoilApi::class)
@Composable
internal fun MultipleChoiceIconQuestionCMP(

    options: List<String>,
    //questionState: MutableState<Boolean>,
    url: List<String>,
    blurhash: List<String>,
    answer: MutableList<String>,
    onAnswerSelected: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    //questionState.value = true
    Column(modifier = modifier) {
        var i = 0
        for (option in options) {
            var checkValue = false
            if(answer.size > i){
                if(answer[i]== "1"){
                    checkValue = true
                }
            }
            var checkedState by remember { mutableStateOf(checkValue) }
            i += 1
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
                                //questionState.value = true
                            }
                        )
                        .padding(vertical = 16.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    //Esta es la forma en la que se puede solicitar el acceso a los recuresos que se ponen en la carpeta
                    //drawable
                    val resources = LocalContext.current.resources
                    //Log.d("image",blurhash[blurhash.indexOf(option)])
                    Log.d("image",url[options.indexOf(option)])
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