package ca.centennial.finalproyect.utils

import android.app.appsearch.AppSearchManager.SearchContext
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EdamamAPIService {
    @GET("search")
    suspend fun  searchRecipe(
        @Query("query") query: String,
        @Query("app_id") appID: String,
        @Query("app_key") appKey: String
    ): Response<SearchContext>
}