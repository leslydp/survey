package com.leslydp.surveylib.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
internal fun SliderQuestionCMP(
    options: List<String>,
   // questionState: MutableState<Boolean>,
    onAnswerSelected: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    var sliderPosition by remember {
        mutableStateOf(0f)
    }
    //questionState.value = true
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