package ca.centennial.finalproyect.ui.screens.home_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ca.centennial.finalproyect.R
import ca.centennial.finalproyect.utils.MealType

@Composable
fun AddViewMeal(
    onAddMealClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    var showAddBreakfastView by remember {
        mutableStateOf(false)
    }
    var showAddLunchView by remember {
        mutableStateOf(false)
    }
    var showAddDinnerView by remember {
        mutableStateOf(false)
    }
    var showAddSnacksView by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
    ) {
        MealType(MealType.BREAKFAST, R.drawable.ic_breakfast, "Food", showAddBreakfastView, {
            showAddBreakfastView = !showAddBreakfastView
        }, onAddMealClick = onAddMealClick)
        MealType(MealType.LUNCH, R.drawable.ic_lunch, "Food", showAddLunchView, {
            showAddLunchView = !showAddLunchView
        }, onAddMealClick = onAddMealClick)
        MealType(MealType.DINNER, R.drawable.ic_dinner, "Food", showAddDinnerView, {
            showAddDinnerView = !showAddDinnerView
        }, onAddMealClick = onAddMealClick)
        MealType(MealType.SNACK, R.drawable.ic_snack, "Food", showAddSnacksView, {
            showAddSnacksView = !showAddSnacksView
        }, onAddMealClick = onAddMealClick)
    }
}

@Preview
@Composable
fun PreviewAddViewMeal() {
    AddViewMeal({})
}