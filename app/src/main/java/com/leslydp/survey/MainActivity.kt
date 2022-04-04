package com.leslydp.survey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.leslydp.survey.ui.theme.SurveyTheme
import com.leslydp.surveylib.JetsurveyScreen
import com.leslydp.surveylib.model.Question


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
                    var ans: Array<String>
                    var options = listOf<String>("option 1", "option 2", "option 3","option 4")
                    val preguntas= listOf<Question>(
                        Question(id = 2, "hola",options)
                    )

                    JetsurveyScreen(preguntas) { answer ->
                        ans = answer.ans
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