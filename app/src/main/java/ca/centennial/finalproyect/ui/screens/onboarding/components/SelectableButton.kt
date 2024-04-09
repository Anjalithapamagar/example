package ca.centennial.finalproyect.ui.screens.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SelectableButton(
    buttonText: String,
    isSelected: Boolean,
    defaultColor: Color,
    selectedColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(40.dp))
            .border(color = selectedColor, shape = RoundedCornerShape(40.dp), width = 2.dp)
            .clickable {
                onClick()
            }
            .background(if (isSelected) Color.Green else Color.Transparent)
            .padding(bottom = 10.dp, start = 20.dp, end = 20.dp, top = 10.dp)
    ) {
        Text(
            text = buttonText,
            fontWeight = FontWeight.Bold,
            color = if (isSelected) defaultColor else selectedColor
        )
    }
}

@Composable
@Preview
fun ShowPreview() {
    SelectableButton(
        "Male",
        true,
        Color.White,
        selectedColor = Color.Green
    ) {

    }
}