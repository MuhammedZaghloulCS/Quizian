@file:OptIn(ExperimentalMaterial3Api::class)

package com.myownportfolio.quizian

import CustomTopAppBar
import Question
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.EditProcessor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myownportfolio.quizian.ui.theme.QuizianTheme

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizianTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { CustomTopAppBar() }
                ) {
                    Column(modifier = Modifier.fillMaxSize()
                        .background(brush = Brush.verticalGradient(listOf(
                        Color(0xFF3700B3),
                        Color(0xFF6200EE) // Gradient start color
                    )
                        )
                    )
                    ) {
                        Question()


                    }


                }
            }
        }
    }
}
