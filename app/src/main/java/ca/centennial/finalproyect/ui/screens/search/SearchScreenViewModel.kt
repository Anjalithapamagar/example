package ca.centennial.finalproyect.ui.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.centennial.finalproyect.data.FoodRepository
import ca.centennial.finalproyect.data.mapper.toFoodEntity
import ca.centennial.finalproyect.data.remote.repository.SearchRepository
import ca.centennial.finalproyect.domain.TrackableFood
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val foodRepository: FoodRepository
) : ViewModel() {

    var state by mutableStateOf(SearchState())
        private set

    var userInput by mutableStateOf("")
        private set

    var shouldShowHint by mutableStateOf(userInput.isEmpty())
        private set

    fun onUserEnterValue(value: String) {
        userInput = value
        shouldShowHint = userInput.isEmpty()
    }

    fun searchFood() {
        Timber.i("Searching.. ${userInput}")
        viewModelScope.launch {
            state = state.copy(
                trackableFood = emptyList(), isSearching = true
            )
            searchRepository.searchFood(userInput, 1, 20).onSuccess { data ->
                state = state.copy(
                    trackableFood = data, isSearching = false, query = ""
                )
            }
        }
    }

    fun addItemToDatabase(foodItem: TrackableFood, mealType: Int, quantity: Int) {
        viewModelScope.launch {
            foodRepository.insertFood(foodItem.toFoodEntity(mealType, quantity))
        }
    }

    fun getAllFoods(mealType: Int) {
        viewModelScope.launch {
            foodRepository.getAllFoods(mealType).collect { it ->
                Timber.i("mealType : ${mealType} : ", it.toString())
                Timber.i(it.toString())
            }
        }
    }
}