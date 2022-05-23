package com.leslydp.surveyactivity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.leslydp.surveyactivity.ui.theme.SurveyTheme
import com.leslydp.surveylib.SurveyQuestionsScreen
import com.leslydp.surveylib.model.SQuestion
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
                    var option2 = listOf<String>("https://blurha.sh/assets/images/img1.jpg[!]LEHV6nWB2yk8pyo0adR*.7kCMdnj[!]option1","https://blurha.sh/assets/images/img1.jpg[!]LEHV6nWB2yk8pyo0adR*.7kCMdnj[!]option2","https://blurha.sh/assets/images/img1.jpg[!]LEHV6nWB2yk8pyo0adR*.7kCMdnj[!]option3")
                    var options = listOf<String>("option1","option2","option3")
                    val preguntas= listOf<SQuestion>(
                        SQuestion.TextQuestion("Hola4"),
                        SQuestion.SingleChoiceQuestion("hola", options ),
                        SQuestion.MultipleChoiceQuestion("Hola2",option2),
                        SQuestion.SliderQuestion("Hola3",options)

                    )

                    SurveyQuestionsScreen(

                        {
                            finish()
                        },
                        {},
                        { answer ->
                        ans = answer.ans
                        Log.d("repuesta",ans.toString())
                    }
                    )
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