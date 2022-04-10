package com.leslydp.survey

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.leslydp.survey.ui.theme.SurveyTheme
import com.leslydp.surveylib.JetsurveyScreen
import com.leslydp.surveylib.SurveyQuestionsScreen
import com.leslydp.surveylib.model.SQuestion


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SurveyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var ans: List<String>
                    var option2 = listOf<String>("option1", "option2", "option3")
                    var options = listOf<String>("option1","option2","option3")
                    val preguntas= listOf<SQuestion>(
                        SQuestion.SingleChoiceQuestion("hola", options ),
                        SQuestion.MultipleChoiceQuestion("Hola2",option2),
                        SQuestion.SliderQuestion("Hola3",options),
                        SQuestion.TextQuestion("Hola4")
                    )

                    SurveyQuestionsScreen(preguntas,{finish()},{}) { answer ->
                        ans = answer.ans
                        Log.d("repuesta",ans.toString())
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SurveyTheme {
        Greeting("Android")
    }
}