package ca.centennial.finalproyect.di

import ca.centennial.finalproyect.data.FoodRepository
import ca.centennial.finalproyect.data.local.database.FoodDatabase
import ca.centennial.finalproyect.data.remote.ApiService
import ca.centennial.finalproyect.data.remote.SearchRepositoryInterface
import ca.centennial.finalproyect.data.remote.repository.SearchRepository
import ca.centennial.finalproyect.data.repository.FoodRepositoryImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideSearchRepository(api: ApiService): SearchRepositoryInterface {
        return SearchRepository(api)
    }

    @Provides
    @Singleton
    fun provideFoodRepository(
        foodDatabase: FoodDatabase,
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
    ): FoodRepository {
        return FoodRepositoryImplementation(foodDatabase, defaultDispatcher)
    }
}