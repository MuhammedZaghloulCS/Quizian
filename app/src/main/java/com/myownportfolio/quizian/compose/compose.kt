@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myownportfolio.quizian.R

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CustomTopAppBar() {
   return TopAppBar(
            title = {

                    Text(
                        text = "Quizian",
                        color = Color.White,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start = 20.dp)
                    )
                }
            ,
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF6200EE), // Gradient start color
                            Color(0xFF3700B3)  // Gradient end color
                        )
                    )
                )
                .padding(horizontal = 16.dp),
            colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,

            ),navigationIcon = {
                Image(
                    painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription = "Back Button",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { /* Handle back navigation */ }
                )
            },
       actions = {
           // Second image on the right
           Text("1/1", color = Color.White, fontSize = 20.sp)
       }

        )

}
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Question(modifier: Modifier = Modifier) {
    var text by remember {
        mutableStateOf("")
    }
    Box(modifier=Modifier.fillMaxWidth().padding(top = 80.dp), contentAlignment = Alignment.Center)
    {

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            modifier = modifier.shadow(24.dp, spotColor = Color.White)
                .fillMaxWidth(0.9f) // Sets the width of the TextField to 90% of the screen
                , textStyle = textStyle() //redefine the text style
              , placeholder = {
                Text(
                    "Write Your Question Here",
                    style = textStyle(),
                    modifier = Modifier.align(Alignment.Center).fillMaxWidth()
                ) // Ensure the placeholder is also centered
            } //the equivalent to hint in XMl

        )

    }
}
fun textStyle() = androidx.compose.ui.text.TextStyle(
    color = Color.White,
    fontSize = 25.sp,
    textAlign = TextAlign.Center, // Ensures the text is centered horizontally
    fontFamily = FontFamily(Font(R.font.allan_bold))
)