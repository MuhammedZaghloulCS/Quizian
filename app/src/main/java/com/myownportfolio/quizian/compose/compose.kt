import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myownportfolio.quizian.MVVM.SetQuizViewModel
import com.myownportfolio.quizian.R
import com.myownportfolio.quizian.ui.theme.QuizianTheme
import com.myownportfolio.quizian.ui.theme.textStyle
import com.myownportfolio.quizian.ui.theme.textStyle2


@ExperimentalMaterial3Api
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun QuizScreen(context: Context) {
    val myViewModel: SetQuizViewModel by viewModels()
    QuizianTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { CustomTopAppBar() },
        ) { innerPadding ->
            // Column to fill the whole screen with the gradient background
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color(0xFF3700B3), Color(0xFF6200EE)
                            )
                        )
                    )
                    .padding(16.dp)
                    .padding(innerPadding) // Apply Scaffold's inner padding here
                // Additional padding for content
            ) {
                // Content of the column
                Question()
                Hint()
                Answers()
                Spacer(modifier = Modifier.height(16.dp)) // Add spacing between sections
                FinishOrAnotherQuestion(context)
            }
        }
    }
}

//TopAppBar
@ExperimentalMaterial3Api
@Composable
fun CustomTopAppBar() {
    return TopAppBar(title = {

        Text(
            text = "Quizian",
            color = Color.White,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 20.dp)
        )
    }, modifier = Modifier
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF6200EE), // Gradient start color
                    Color(0xFF3700B3)  // Gradient end color
                )
            )
        )
        .padding(horizontal = 16.dp), colors = TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Transparent,

        ), navigationIcon = {
        Image(painter = painterResource(id = R.drawable.back_arrow),
            contentDescription = "Back Button",
            modifier = Modifier
                .size(24.dp)
                .clickable { /* Handle back navigation */ })
    }, actions = {
        // Second image on the right
        Text("1/1", color = Color.White, fontSize = 20.sp)
    }

    )

}

//Taking the main Question
@Composable
fun Question(modifier: Modifier = Modifier) {
    var text by rememberSaveable {
        mutableStateOf("")
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 80.dp), contentAlignment = Alignment.Center
    ) {

        OutlinedTextField(value = text,
            onValueChange = { text = it },
            modifier = modifier
                .shadow(24.dp, spotColor = Color.White)
                .fillMaxWidth(0.9f) // Sets the width of the TextField to 90% of the screen
            ,
            textStyle = textStyle() //redefine the text style
            ,
            placeholder = {
                Text(
                    stringResource(R.string.enter_question),
                    style = textStyle(),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth()
                ) // Ensure the placeholder is also centered
            } //the equivalent to hint in XMl

        )

    }
}

@Composable
fun Hint() {
    Text(
        stringResource(R.string.hint),
        style = textStyle2(),
        modifier = Modifier.padding(top = 25.dp),
        color = Color.Gray
    )
}

@Composable
fun Answers() {
    var q1 by rememberSaveable { mutableStateOf("") }
    var q2 by rememberSaveable { mutableStateOf("") }
    var q3 by rememberSaveable { mutableStateOf("") }
    var q4 by rememberSaveable { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        OutlinedTextField(value = q1,
            onValueChange = { q1 = it },
            textStyle = textStyle2(),
            modifier = Modifier.weight(1f),
            placeholder = {
                Text(
                    stringResource(R.string.enter_answer), style = textStyle2()
                )
            })
        Spacer(Modifier.padding(horizontal = 10.dp))
        OutlinedTextField(value = q2,
            onValueChange = { q2 = it },
            textStyle = textStyle(),
            modifier = Modifier.weight(1f),
            placeholder = {
                Text(
                    stringResource(R.string.enter_answer), style = textStyle2()
                )
            })
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        OutlinedTextField(value = q3,
            onValueChange = { q3 = it },
            textStyle = textStyle(),
            modifier = Modifier.weight(1f),
            placeholder = {
                Text(
                    stringResource(R.string.enter_answer), style = textStyle2()
                )
            })
        Spacer(Modifier.padding(horizontal = 10.dp))

        OutlinedTextField(value = q4,
            onValueChange = { q4 = it },
            textStyle = textStyle(),
            modifier = Modifier.weight(1f),
            placeholder = {
                Text(
                    stringResource(R.string.enter_answer), style = textStyle2()
                )
            })
    }

}

@Composable
fun FinishOrAnotherQuestion(
    onFinishClick: () -> Unit,
    onAnotherQuestionClick: () -> Unit,
    context: Context,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp), // Add padding to avoid buttons sticking to the edge
        contentAlignment = Alignment.BottomCenter // Align content to the bottom-center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { onFinishClick() }, Modifier.size(200.dp, 50.dp)) {
                Text(text = "Finish", fontSize = 20.sp)
            }
            Button(onClick = { onAnotherQuestionClick() }, Modifier.size(200.dp, 50.dp)) {
                Text(text = "Another Question", fontSize = 18.sp)
            }
        }
    }
}



