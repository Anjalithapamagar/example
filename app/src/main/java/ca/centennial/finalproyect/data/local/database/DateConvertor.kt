package ca.centennial.finalproyect.data.local.database

import androidx.room.TypeConverter
import java.util.Date

class DateConvertor {

    @TypeConverter
    fun toTimeStamp(date: Date): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun fromTimeStamps(timeStamp: Long): Date? {
        return timeStamp?.let {
            Date(it)
        }
    }
}