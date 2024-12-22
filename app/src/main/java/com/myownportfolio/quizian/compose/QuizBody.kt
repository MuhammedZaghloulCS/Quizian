package com.myownportfolio.quizian.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myownportfolio.quizian.R
import com.myownportfolio.quizian.ui.theme.textStyle

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun QuizBody(){
    Column(modifier = Modifier.padding(horizontal = 16.dp).background(colorResource(R.color.purple_200))) {
        Questions({},{})
    }

}

@Composable
fun Questions(addAnotherAnswer: () -> Unit,removeAnswer:()->Unit) {
    var trdVis by remember { mutableStateOf(false) }
    var frthVis by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxWidth()) {

            Text(text = "<< Add Another Answer", Modifier.clickable { addAnotherAnswer() }.align(
                alignment = Alignment.Start
            ), color = Color.White)


        Spacer(Modifier.padding(vertical = 16.dp))

            Text(text = "Remove Answer >>", Modifier.clickable { removeAnswer() }.align(Alignment.End), color = Color.White)

     /*   Row(modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally), horizontalArrangement = Arrangement.SpaceEvenly){

            var text1 by remember { mutableStateOf("") }
            var text2 by remember { mutableStateOf("") }
            var text3 by remember { mutableStateOf("") }
            var text4 by remember { mutableStateOf("") }
            Spacer(Modifier.padding(horizontal = 10.dp))
            TextField(value = text1, onValueChange = { text1 = it },Modifier.weight(1f)
                , placeholder = {
                    Text(text = "Enter the Answer Here", color = Color.White, style = textStyle(15))
                })
            Spacer(Modifier.padding(horizontal = 10.dp))

            TextField(value = text2, onValueChange = { text2 = it },Modifier.weight(1f)
                , placeholder = {
                    Text(text = "Enter the Answer Here", color = Color.White, style = textStyle(15))
                })
            Spacer(Modifier.padding(horizontal = 10.dp))



        }
    */
    }
}

