package ca.centennial.finalproyect.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [FoodEntity::class], exportSchema = false, version = 1)
@TypeConverters(DateConvertor::class)
abstract class FoodDatabase : RoomDatabase() {
    abstract fun provideFoodDao(): FoodDao
}