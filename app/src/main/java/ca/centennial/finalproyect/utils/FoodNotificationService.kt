package ca.centennial.finalproyect.utils

import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import ca.centennial.finalproyect.R
import kotlin.random.Random

class FoodNotificationService(
    private val context: Context
) {
    private val notificationManager=context.getSystemService(NotificationManager::class.java)
    fun showBasicNotification(){
        val notification= NotificationCompat.Builder(context,"food_notification")
            .setContentTitle("Food Remainder")
            .setContentText("Time to have a balanced diet food!")
            .setSmallIcon(R.drawable.food_icon)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            Random.nextInt(),
            notification
        )
    }

    fun showExpandableNotification(){
        val notification=NotificationCompat.Builder(context,"food_notification")
            .setContentTitle("Food Remainder")
            .setContentText("Time to have a balanced diet food!")
            .setSmallIcon(R.drawable.food_icon)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .setStyle(
                NotificationCompat
                    .BigPictureStyle()
                    .bigPicture(
                        context.bitmapFromResource(
                            R.drawable.food_remainder
                        )
                    )
            )
            .build()
        notificationManager.notify(Random.nextInt(),notification)
    }

    private fun Context.bitmapFromResource(
        @DrawableRes resId:Int
    )= BitmapFactory.decodeResource(
        resources,
        resId
    )
}
