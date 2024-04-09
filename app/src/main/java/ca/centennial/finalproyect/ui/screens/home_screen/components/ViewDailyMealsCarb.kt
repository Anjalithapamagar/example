package ca.centennial.finalproyect.ui.screens.home_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.centennial.finalproyect.R

@Composable
fun ViewDailyMealsCarb(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Color(0xFF00C713),
                shape = RoundedCornerShape(
                    bottomEnd = 40.dp,
                    bottomStart = 40.dp
                )
            )
    ) {
        Row(
            modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.Bottom
        ) {
            Text(
                fontSize = 18.sp, color = Color.White, text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 35.sp
                        )
                    ) {
                        append("0")
                    }
                    append(" " + stringResource(R.string.kcal))
                }, modifier = Modifier.weight(1f)
            )
            Column() {
                Text(
                    fontSize = 18.sp, color = Color.White, text = "Your goal:"
                )
                Text(fontSize = 18.sp, color = Color.White, text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 35.sp
                        )
                    ) {
                        append("2510")
                    }
                    append(" " + stringResource(R.string.kcal))
                })
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            CaloryItem(
                count = "0",
                unit = stringResource(R.string.unit_carbs),
                modifier = Modifier
                    .weight(1.0f)
                    .padding(10.dp)
            )
            CaloryItem(
                count = "0", unit = stringResource(R.string.protein),
                modifier = Modifier
                    .weight(1.0f)
                    .padding(10.dp)
            )
            CaloryItem(
                count = "0", unit = stringResource(R.string.fat),
                modifier = Modifier
                    .weight(1.0f)
                    .padding(10.dp)
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Preview
@Composable
fun PreviewViewDailyMealsCarb() {
    ViewDailyMealsCarb()
}