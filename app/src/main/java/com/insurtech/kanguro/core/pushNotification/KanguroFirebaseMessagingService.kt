package com.insurtech.kanguro.core.pushNotification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.insurtech.kanguro.R
import com.insurtech.kanguro.core.utils.KanguroConstants
import com.insurtech.kanguro.core.utils.sendLocalBroadcast
import com.insurtech.kanguro.ui.StartActivity

class KanguroFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        const val CHANNEL_ID = "firebase_messaging_channel"
        const val CHANNEL_NAME = "firebase_messaging_notification"
    }

    override fun onNewToken(token: String) {
        this.sendLocalBroadcast(KanguroConstants.BROADCAST_ON_NEW_FIREBASE_TOKEN)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        message.notification?.let {
            createNotificationChannel()
            val title = it.title ?: ""
            val text = it.body ?: ""
            createNotification(title, text)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            getSystemService(NotificationManager::class.java).createNotificationChannel(
                notificationChannel
            )
        }
    }

    private fun createNotification(title: String, message: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notifyIntent = Intent(this, StartActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            val notifyPendingIntent = PendingIntent.getActivity(
                this,
                0,
                notifyIntent,
                PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val notification = Notification.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_kanguro_toolbar)
                .setAutoCancel(true)
                .setContentIntent(notifyPendingIntent)

            val id = System.currentTimeMillis().toInt()
            NotificationManagerCompat.from(this).notify(id, notification.build())
        }
    }
}
