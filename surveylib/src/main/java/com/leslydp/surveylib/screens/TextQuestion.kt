package com.leslydp.surveylib.screens

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun TextQuestionCMP(
    onAnswerWritten: (String) -> Unit,
    modifier: Modifier = Modifier,
    questionState: MutableState<Boolean>
) {
    val textState = remember { mutableStateOf(TextFieldValue()) }
    val qState = remember{ mutableStateOf(false) }
    questionState.value = qState.value
    OutlinedTextField(modifier = Modifier
        .fillMaxWidth()
        .height(200.dp),
        placeholder = { Text("Some description") },
        value = textState.value,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            cursorColor = Color.Black,
            disabledLabelColor = Color.White
        ),
        onValueChange = {
            textState.value = it
            qState.value = textState.value.text != ""
            Log.d("estado",questionState.value.toString())
            onAnswerWritten(textState.value.text)
        })



}