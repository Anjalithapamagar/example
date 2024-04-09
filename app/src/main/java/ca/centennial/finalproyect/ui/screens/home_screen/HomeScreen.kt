package ca.centennial.finalproyect.ui.screens.home_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ca.centennial.finalproyect.ui.screens.home_screen.components.AddViewMeal
import ca.centennial.finalproyect.ui.screens.home_screen.components.CalendarView
import ca.centennial.finalproyect.ui.screens.home_screen.components.ViewDailyMealsCarb

@Composable
fun HomeScreen_2(onAddMealClick: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ViewDailyMealsCarb()
        CalendarView("Today", onPrevClick = {

        }, onNextClick = {

        })
        AddViewMeal(onAddMealClick = onAddMealClick)
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen_2({})
}