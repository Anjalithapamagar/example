package ca.centennial.finalproyect.ui.screens.home_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CalorieCounter(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = "0g", textAlign = TextAlign.Center)
            Text(text = "0g", textAlign = TextAlign.Center)
            Text(text = "0g", textAlign = TextAlign.Center)
        }
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = "Carbs", textAlign = TextAlign.Center)
            Text(text = "Protein", textAlign = TextAlign.Center)
            Text(text = "Fat", textAlign = TextAlign.Center)
        }
    }
}

@Preview
@Composable
fun PreviewCalorieCounter() {
    CalorieCounter()
}