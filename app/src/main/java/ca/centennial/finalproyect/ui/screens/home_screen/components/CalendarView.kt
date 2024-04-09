package ca.centennial.finalproyect.ui.screens.home_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.centennial.finalproyect.R


@Composable
fun CalendarView(
    dayString: String,
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {

        Icon(
            Icons.Default.ArrowBack,
            contentDescription = stringResource(R.string.prev_day),
            modifier = Modifier
                .align(alignment = Alignment.CenterStart)
                .clickable {
                    onPrevClick()
                }
                .padding(10.dp)
        )

        Text(
            text = dayString,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(alignment = Alignment.Center)
        )

        Icon(
            Icons.Default.ArrowForward,
            contentDescription = stringResource(R.string.next_day),
            modifier = Modifier
                .align(alignment = Alignment.CenterEnd)
                .clickable {
                    onNextClick()
                }
                .padding(10.dp)
        )
    }
}

@Preview
@Composable
fun PreviewCalendarView() {
    CalendarView(dayString = "Today", {}, {})
}