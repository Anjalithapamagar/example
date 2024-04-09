package ca.centennial.finalproyect.ui.screens.home_screen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CaloryItem(count: String, unit: String, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
        modifier
            .fillMaxSize()
            .clip(shape = CircleShape)
            .border(
                width = 7.dp,
                color = Color.White,
                shape = CircleShape
            )
            .padding(15.dp)
    ) {
        Text(color = Color.White, fontSize = 18.sp, text = buildAnnotatedString {
            withStyle(SpanStyle(fontSize = 22.sp)) {
                append(count)
            }

        })
        Text(text = unit, color = Color.White, fontSize = 18.sp)
    }

}

@Preview
@Composable
fun PreviewCaloryItem() {
    CaloryItem(count = "20", unit = "Proteins")
}