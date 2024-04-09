package ca.centennial.finalproyect.data.remote

import ca.centennial.finalproyect.domain.TrackableFood

interface SearchRepositoryInterface {
    suspend fun searchFood(
        query: String,
        page: Int = 1,
        pageSize: Int = 40
    ): Result<List<TrackableFood>>
}