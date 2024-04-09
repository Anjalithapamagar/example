package ca.centennial.finalproyect.ui.screens.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ca.centennial.finalproyect.R
import ca.centennial.finalproyect.ui.screens.search.components.FoodItem
import ca.centennial.finalproyect.ui.screens.search.components.SearchFoodField
import ca.centennial.finalproyect.utils.MealType

@Composable
fun SearchScreen(
    snackbarHostState: SnackbarHostState,
    mealType: Int,
    viewModel: SearchScreenViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .padding(start = 24.dp, top = 24.dp, end = 24.dp)
            .fillMaxSize()
    ) {
        Text(text = "Add ${MealType.fromType(mealType)}", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(10.dp))
        SearchFoodField(
            value = viewModel.userInput,
            hint = stringResource(R.string.hint_search),
            shouldShowHint = viewModel.shouldShowHint,
            onValueChange = {
                viewModel.onUserEnterValue(it)
            },
            onSearchFood = {
                viewModel.searchFood()
            }
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(viewModel.state.trackableFood) { food ->
                FoodItem(food, onItemClick = {
                    viewModel.addItemToDatabase(food, mealType, 1)
                })
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when {
            viewModel.state.isSearching -> CircularProgressIndicator()
            viewModel.state.trackableFood.isEmpty() -> {
                Text(text = stringResource(R.string.no_food), textAlign = TextAlign.Center)
            }
        }
    }
}
