package ca.centennial.finalproyect.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import ca.centennial.finalproyect.data.local.DefaultSharedPreferences
import ca.centennial.finalproyect.data.local.SharedPreferencesInterface
import ca.centennial.finalproyect.data.local.database.FoodDao
import ca.centennial.finalproyect.data.local.database.FoodDatabase
import ca.centennial.finalproyect.utils.Constants

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideDefaultSharedPreferences(sharedPreferences: SharedPreferences): SharedPreferencesInterface {
        return DefaultSharedPreferences(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideFoodDatabase(@ApplicationContext context: Context): FoodDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = FoodDatabase::class.java,
            name = Constants.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideFoodDao(database: FoodDatabase): FoodDao {
        return database.provideFoodDao()
    }

}