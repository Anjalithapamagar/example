package ca.centennial.finalproyect.ui.screens.onboarding.nutrient_goal

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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ca.centennial.finalproyect.R
import ca.centennial.finalproyect.ui.screens.onboarding.components.ActionButton
import ca.centennial.finalproyect.ui.screens.onboarding.components.UnitTextField
import ca.centennial.finalproyect.utils.UiEvent

@Composable
fun NutrientGoalScreen(
    snackbarHostState: SnackbarHostState,
    onNextClick: () -> Unit,
    viewModel: NutrientViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when (it) {
                UiEvent.NavigateUser -> {
                    onNextClick()
                }

                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = "Please select correct nutrients level",
                        actionLabel = "",
                        withDismissAction = true,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.whats_your_nutrient_goal),
                color = Color.White,
                fontSize = 26.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.padding(10.dp))

            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center,
            ) {
                UnitTextField(value = viewModel.carbsPercentage, onValueChange = {
                    viewModel.updateCarbsPercentage(it)
                })

                Text(text = stringResource(R.string.carbs), color = Color.White)
            }

            Spacer(modifier = Modifier.padding(10.dp))

            Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center) {
                UnitTextField(value = viewModel.proteinsPercentage, onValueChange = {
                    viewModel.updateProteinPercentage(it)
                })

                Text(text = stringResource(R.string.proteins), color = Color.White)
            }

            Spacer(modifier = Modifier.padding(10.dp))

            Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center) {
                UnitTextField(value = viewModel.fatsPercentage, onValueChange = {
                    viewModel.updateFatsPercentage(it)
                })

                Text(text = stringResource(R.string.fats), color = Color.White)
            }
        }

        ActionButton(buttonText = stringResource(R.string.next),
            buttonColor = ButtonDefaults.buttonColors(Color(0XFF00C713)),
            textColor = Color.White,
            modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
                .padding(24.dp),
            onClick = {
                viewModel.navigate()
            })
    }


}
