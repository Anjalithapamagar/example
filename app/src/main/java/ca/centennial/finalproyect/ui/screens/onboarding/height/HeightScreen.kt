package ca.centennial.finalproyect.ui.screens.onboarding.height

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ca.centennial.finalproyect.R
import ca.centennial.finalproyect.ui.screens.onboarding.components.ActionButton
import ca.centennial.finalproyect.ui.screens.onboarding.components.UnitTextField

@Composable
fun HeightScreen(
    navClickCallback: () -> Unit,
    viewModel: HeightViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = true, block = {
        viewModel.uiState.collect {
            navClickCallback()
        }
    })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.whats_your_height),
                color = Color.White,
                fontSize = 26.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.padding(20.dp))

            Row(verticalAlignment = Alignment.Bottom) {

                UnitTextField(viewModel.userHeight) {
                    viewModel.onEvent(HeightViewModel.HeightStateEvent.UpdateUserHeight(it))
                }

                Text(
                    text = stringResource(R.string.inches),
                    modifier = Modifier.padding(10.dp),
                    color = Color.White
                )
            }
        }

        ActionButton(
            buttonText = stringResource(R.string.next),
            buttonColor = ButtonDefaults.buttonColors(Color(0XFF00C713)),
            textColor = Color.White,
            modifier = Modifier.align(alignment = Alignment.BottomEnd),
            onClick = {
                viewModel.onEvent(HeightViewModel.HeightStateEvent.OnNextClick)
            }
        )
    }
}

@Composable
@Preview
fun ShowPreview() {
    HeightScreen({})
}