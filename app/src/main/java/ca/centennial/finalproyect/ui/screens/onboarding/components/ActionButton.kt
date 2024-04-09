package ca.centennial.finalproyect.ui.screens.onboarding.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ActionButton(
    buttonText: String,
    buttonColor: ButtonColors,
    textColor: Color,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Button(
        onClick = { onClick() },
        colors = buttonColor,
        modifier = modifier
    ) {
        Text(
            text = buttonText,
            color = textColor,
            fontSize = 18.sp
        )
    }
}

@Preview
@Composable
fun ActionButtonPreview() {
    ActionButton(
        "Next",
        buttonColor = ButtonDefaults.buttonColors(Color(0XFF00C713)),
        textColor = Color.White,
        onClick = {},
        modifier = Modifier.padding(bottom = 7.dp, start = 12.dp, end = 12.dp, top = 7.dp)
    )
}