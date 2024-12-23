import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
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
import kotlinx.coroutines.launch


@ExperimentalMaterial3Api
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun QuizScreen(context: Context, viewModel: SetQuizViewModel) {
    // State for inputs
    var questionText by rememberSaveable { mutableStateOf("") }
    var answer1 by rememberSaveable { mutableStateOf("") }
    var answer2 by rememberSaveable { mutableStateOf("") }
    var answer3 by rememberSaveable { mutableStateOf("") }
    var answer4 by rememberSaveable { mutableStateOf("") }

    // State for dialog visibility
    var showConfirmationDialog by rememberSaveable { mutableStateOf(false) }

    // Snackbar state
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    QuizianTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { CustomTopAppBar() },
            snackbarHost = { SnackbarHost(snackbarHostState) },
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(innerPadding)
            ) {
                Question(questionText) { questionText = it }
                Hint()
                Answers(
                    answer1 = answer1,
                    answer2 = answer2,
                    answer3 = answer3,
                    answer4 = answer4,
                    onAnswer1Change = { answer1 = it },
                    onAnswer2Change = { answer2 = it },
                    onAnswer3Change = { answer3 = it },
                    onAnswer4Change = { answer4 = it },
                )
                Spacer(modifier = Modifier.height(16.dp))
                FinishOrAnotherQuestion(
                    onFinishClick = { /* Handle finish */ },
                    onAnotherQuestionClick = {
                        if (questionText.isBlank() || answer1.isBlank() || answer2.isBlank()) {
                            Toast.makeText(
                                context,
                                "Please fill in the question field and at least 2 answers before adding another question.",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            showConfirmationDialog = true // Show dialog
                        }
                    },
                    context = context
                )
            }
        }

        // Confirmation Dialog
        if (showConfirmationDialog) {
            AlertDialog(
                onDismissRequest = { showConfirmationDialog = false },
                title = { Text(text = "Add Question") },
                text = { Text("Are you sure you want to add this question?") },
                confirmButton = {
                    TextButton(onClick = {
                        // Add the question
                        val answers = mutableListOf(answer1, answer2, answer3, answer4)
                            .filter { it.isNotBlank() }
                        viewModel.addQuestion(questionText, answers.toMutableList())

                        Log.d("Question Added", "Question: $questionText, Answers: $answers")

                        // Clear inputs
                        questionText = ""
                        answer1 = ""
                        answer2 = ""
                        answer3 = ""
                        answer4 = ""

                        // Show confirmation message
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Question added successfully!",
                                actionLabel = "OK",
                                duration = SnackbarDuration.Long
                            )
                        }

                        // Close dialog
                        showConfirmationDialog = false
                    }) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showConfirmationDialog = false }) {
                        Text("No")
                    }
                }
            )
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
fun Question(questionText: String, onTextChange: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 80.dp),
        contentAlignment = Alignment.Center
    ) {

        OutlinedTextField(
            value = questionText,
            onValueChange = onTextChange,
            modifier = Modifier
                .shadow(24.dp, spotColor = Color.White)
                .fillMaxWidth(0.9f),
            textStyle = textStyle(),
            placeholder = {
                Text(
                    stringResource(R.string.enter_question),
                    style = textStyle(),
                    modifier = Modifier.fillMaxWidth()
                )
            }
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
fun Answers(
    answer1: String,
    answer2: String,
    answer3: String,
    answer4: String,
    onAnswer1Change: (String) -> Unit,
    onAnswer2Change: (String) -> Unit,
    onAnswer3Change: (String) -> Unit,
    onAnswer4Change: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        OutlinedTextField(
            value = answer1,
            onValueChange = onAnswer1Change,
            textStyle = textStyle2(),
            modifier = Modifier.weight(1f),
            placeholder = { Text(stringResource(R.string.enter_answer), style = textStyle2()) }
        )
        Spacer(Modifier.padding(horizontal = 10.dp))
        OutlinedTextField(
            value = answer2,
            onValueChange = onAnswer2Change,
            textStyle = textStyle(),
            modifier = Modifier.weight(1f),
            placeholder = { Text(stringResource(R.string.enter_answer), style = textStyle2()) }
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(modifier=Modifier.fillMaxWidth().weight(1f).background(Color.Gray).shadow(11.dp).clip(
            RoundedCornerShape(10.dp)
        ))
        {
            OutlinedTextField(
                value = answer3,
                onValueChange = onAnswer3Change,
                textStyle = textStyle(),
                modifier = Modifier,
                placeholder = { Text(stringResource(R.string.enter_answer), style = textStyle2()) }
            )
        }

        Spacer(Modifier.padding(horizontal = 10.dp))
        Box(modifier=Modifier.fillMaxWidth().weight(1f).background(Color.Gray).shadow(11.dp).clip(
            RoundedCornerShape(10.dp)
        ))
        {
            OutlinedTextField(
                value = answer4,
                onValueChange = onAnswer4Change,
                textStyle = textStyle(),
                modifier = Modifier,
                placeholder = { Text(stringResource(R.string.enter_answer), style = textStyle2()) }
            )
        }

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
            Button(
                onClick = {
                    /*onFinishClick() */
                }, colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.Red))
                , modifier = Modifier.size(200.dp, 50.dp)
            ) {
                Text(text = "Finish", fontSize = 20.sp)
            }
            Button(
                onClick = { onAnotherQuestionClick() },
                modifier = Modifier.size(200.dp, 50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.purple_700))
            )
                {
                    Text(text = "Another Question", fontSize = 18.sp)
                }

        }
    }
}



