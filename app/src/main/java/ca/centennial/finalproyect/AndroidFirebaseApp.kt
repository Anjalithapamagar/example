package ca.centennial.finalproyect


import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AndroidFirebaseApp : Application() {
    companion object {
        const val FCM_CHANNEL_ID = "FCM_CHANNEL_ID"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            val fcmChannel = NotificationChannel(ca.centennial.finalproyect.AndroidFirebaseApp.Companion.FCM_CHANNEL_ID, "FCM_Channel", NotificationManager.IMPORTANCE_HIGH)
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(fcmChannel)
        }
        val notificationChannel = NotificationChannel(
            "food_notification",
            "Food",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationChannel.description = "A notification channel for food"
        val notificationManager=getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }
}