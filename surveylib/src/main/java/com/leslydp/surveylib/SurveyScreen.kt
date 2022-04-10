package com.leslydp.surveylib

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.leslydp.surveylib.model.Answer
import com.leslydp.surveylib.model.SQuestion
import com.ondev.imageblurkt_lib.ImageBlur


@OptIn(
    ExperimentalMaterialApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class,
    androidx.compose.animation.ExperimentalAnimationApi::class
)
@Composable
fun JetsurveyScreen(
    question: SQuestion,
    onAnswer: (String) -> Unit
) {
    //var checkedState by remember{mutableStateOf(false)}
    val ans =
        mutableListOf<String>()  //Puede ser VAL porq lo que puede cambiar es el contenido interno y no la referencia al objecto
    //list.forEach { question ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp)
        ) {

            item {
                Spacer(modifier = Modifier.height(32.dp))
                QuestionTitle(question.questionName)
                Spacer(modifier = Modifier.height(24.dp))

                Log.d("myTag2", question.questionName)
                //QuestionTitle(question.questionName)
                //Log.d("tag", question.options.size.toString())
                when (question) {
                    is SQuestion.MultipleChoiceQuestion -> {
                        Log.d("tag",question.options[0])
                        val cantMaxOptions=question.options.size
                        val respuesta = mutableListOf<Byte>()
                        (1..cantMaxOptions).forEach {
                            respuesta.add(0)
                        }
                        if(question.options[0].contains("[!]")) {
                            val blurhash =
                                mutableListOf<String>() //Puede ser VAL porq lo que puede cambiar es el contenido interno y no la referencia al objecto
                            val url =
                                mutableListOf<String>() //Puede ser VAL porq lo que puede cambiar es el contenido interno y no la referencia al objecto
                            val options =
                                mutableListOf<String>() //Puede ser VAL porq lo que puede cambiar es el contenido interno y no la referencia al objecto
                            for (option in question.options) {
                                val optionSplit = option.split("[!]")
                                blurhash.add(optionSplit[1])
                                url.add(optionSplit[0])
                                options.add(optionSplit[2])
                                Log.d("tag",option)
                                Log.d("tag",url.toString())
                            }
                            MultipleChoiceIconQuestionCMP(options, url, blurhash, { idq, valor ->
                                val compareTo = valor.compareTo(false)
                                respuesta[idq] = compareTo.toByte()
                                Log.d("icon", respuesta.toString())
                            }
                            )
                        }

                       else{
                            MultipleChoiceQuestionCMP(question.options, onAnswerSelected = { idq, valor ->
                                val compareTo = valor.compareTo(false)
                                respuesta[idq] = compareTo.toByte()
                                Log.d("noicon", respuesta.toString())

                            })

                       }
                        ans.add(respuesta.toString())
                        onAnswer(ans.toString())

                    }
                    is SQuestion.SingleChoiceQuestion -> {
                        val cantMaxOptions=question.options.size
                        val respuesta = mutableListOf<Byte>()
                        (1..cantMaxOptions).forEach {
                            respuesta.add(0)
                        }
                        if(question.options[0].contains("[!]")) {
                            val blurhash =
                                mutableListOf<String>() //Puede ser VAL porq lo que puede cambiar es el contenido interno y no la referencia al objecto
                            val url =
                                mutableListOf<String>() //Puede ser VAL porq lo que puede cambiar es el contenido interno y no la referencia al objecto
                            val options =
                                mutableListOf<String>() //Puede ser VAL porq lo que puede cambiar es el contenido interno y no la referencia al objecto
                            for (option in question.options) {
                                val optionSplit = option.split("[!]")
                                blurhash.add(optionSplit[1])
                                url.add(optionSplit[0])
                                options.add(optionSplit[2])
                                Log.d("tag", option)
                                Log.d("tag", url.toString())
                            }
                            SingleChoiceIconQuestionCMP(
                                options = options,
                                url = url,
                                blurhash = blurhash,
                                onAnswerSelected = {id ->
                                    if (respuesta.indexOf(1) > -1)
                                        respuesta[respuesta.indexOf(1)] = 0
                                    respuesta[id] = 1
                                    Log.d("myTag", respuesta.toString())
                                }
                            )
                        }
                        else{

                            SingleChoiceQuestionCMP(question.options, onAnswerSelected = { id ->
                                if (respuesta.indexOf(1) > -1)
                                    respuesta[respuesta.indexOf(1)] = 0
                                respuesta[id] = 1
                                Log.d("myTag", respuesta.toString())

                            })
                        }
                        ans.add(respuesta.toString())
                        onAnswer(ans.toString())

                    }
                    is SQuestion.SliderQuestion -> {
                        var respuesta = 0f
                        SliderQuestionCMP(question.options, onAnswerSelected = {
                            Log.d("slider", it.toString())
                            respuesta = it

                        })
                        ans.add("[!]${respuesta}")
                        onAnswer(ans.toString())
                    }

                    is SQuestion.TextQuestion-> {
                        var respuesta=""
                        TextQuestionCMP(onAnswerWritten = {
                            Log.d("text", it)
                        })
                        ans.add(respuesta)
                        onAnswer(ans.toString())
                    }


                }
            }
        }
   // }


}

@Composable
fun SurveyQuestionsScreen(
    questionsList: List<SQuestion>,
    onBackPressed: () -> Unit,
    onDonePressed: () -> Unit,
    onAnswer: (Answer) -> Unit
) {
    val questionindex = remember{ mutableStateOf(0)}

    val ans =
        mutableListOf<String>()

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
                JetsurveyScreen(questionsList[questionindex.value], onAnswer = {ans.add(it)})
            },
            bottomBar = {
                SurveyBottomBar(
                    questionIndex = questionindex.value,
                    questionSize = questionsList.size,
                    onPreviousPressed = { questionindex.value -= 1 },
                    onNextPressed = { questionindex.value += 1 },
                    onDonePressed = onDonePressed
                )
            }
        )
    }
    val answer = Answer(ans)
    onAnswer(answer)
}

@Composable
private fun TopAppBarTitle(
    questionIndex: Int,
    totalQuestionsCount: Int,
    modifier: Modifier = Modifier
) {
    val indexStyle = MaterialTheme.typography.caption.toSpanStyle().copy(
        fontWeight = FontWeight.Bold
    )
    val totalStyle = MaterialTheme.typography.caption.toSpanStyle()
    val text = buildAnnotatedString {
        withStyle(style = indexStyle) {
            append("${questionIndex + 1}")
        }
        withStyle(style = totalStyle) {
            append("of $totalQuestionsCount")
        }
    }
    Text(
        text = text,
        style = MaterialTheme.typography.caption,
        modifier = modifier
    )
}

@Composable
private fun SurveyTopAppBar(
    questionIndex: Int,
    totalQuestionsCount: Int,
    onBackPressed: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.fillMaxWidth()) {
            TopAppBarTitle(
                questionIndex = questionIndex,
                totalQuestionsCount = totalQuestionsCount,
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .align(Alignment.Center)
            )

            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                IconButton(
                    onClick = onBackPressed,
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                        .fillMaxWidth()
                ) {
                    //val resources = LocalContext.current.resources
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = "close",
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }
            }
        }
        val animatedProgress by animateFloatAsState(
            targetValue = (questionIndex + 1) / totalQuestionsCount.toFloat(),
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
        )
        LinearProgressIndicator(
            progress = animatedProgress,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            backgroundColor =  Color.Black.copy(alpha = 0.12f)
        )
    }
}

@Composable
private fun SurveyBottomBar(
    //questionState: QuestionState,
    questionSize: Int,
    questionIndex: Int,
    onPreviousPressed: () -> Unit,
    onNextPressed: () -> Unit,
    onDonePressed: () -> Unit
) {
    Surface(
        elevation = 7.dp,
        modifier = Modifier.fillMaxWidth() // .border(1.dp, MaterialTheme.colors.primary)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            if (questionIndex != 0) {
                OutlinedButton(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    onClick = onPreviousPressed
                ) {
                    Text(text = "PREVIOUS")
                }
                Spacer(modifier = Modifier.width(16.dp))
            }
            if (questionIndex == questionSize-1) {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    onClick = onDonePressed,
                    enabled = true
                ) {
                    Text(text = "DONE")
                }
            } else {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    onClick = onNextPressed,
                    enabled = true
                ) {
                    Text(text = "NEXT")
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

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun MultipleChoiceIconQuestionCMP(

    options: List<String>,
    url: List<String>,
    blurhash: List<String>,
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

@Composable
private fun SingleChoiceQuestionCMP(
    options: List<String>,
    onAnswerSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var checkedState by remember { mutableStateOf(0) }

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
private fun SingleChoiceIconQuestionCMP(
    options: List<String>,
    url: List<String>,
    blurhash: List<String>,
    onAnswerSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var checkedState by remember { mutableStateOf(-1) }

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
    OutlinedTextField(modifier = Modifier
        .fillMaxWidth()
        .height(200.dp),
        placeholder = { Text("Some description") },
        value = textState.value,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = White,
            cursorColor = Color.Black,
            disabledLabelColor = White
        ),
        //shape = RoundedCornerShape(100.dp),
        onValueChange = {
            textState.value = it
            onAnswerWritten(textState.value.text)
        })



}