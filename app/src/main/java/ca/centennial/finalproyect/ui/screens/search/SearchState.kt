package ca.centennial.finalproyect.ui.screens.search

import ca.centennial.finalproyect.domain.TrackableFood


data class SearchState(
    val query: String = "",
    val isHintVisible: Boolean = false,
    val isSearching: Boolean = false,
    val trackableFood: List<TrackableFood> = emptyList()
)