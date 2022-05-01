package com.example.sshop_sneakershop.Notification.controllers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.sshop_sneakershop.Product.views.ProductDetail
import com.example.sshop_sneakershop.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseInstanceIdController : FirebaseMessagingService() {

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "com.example.sshop_sneakershop"
        const val NOTIFICATION_ID = 100
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val title = remoteMessage.notification?.title
        val body = remoteMessage.notification?.body
        val itemId: String?

        remoteMessage.data.let { itemId = it["item-id"] }
        itemId?.let { showNotification(applicationContext, title, body, it) }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.e("FCM", "Refreshed token: $token")
    }

    private fun showNotification(
        context: Context,
        title: String?,
        message: String?,
        itemId: String
    ) {
        val intent = Intent(context, ProductDetail::class.java)
        intent.putExtra("item-id", itemId)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notification =
            NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setOngoing(true)
                .setSmallIcon(R.drawable.logo_size)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.logo_size_invert
                    )
                )
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setWhen(System.currentTimeMillis())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentTitle(title).build()

        val notificationManager = context.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        val notificationChannel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            title,
            NotificationManager.IMPORTANCE_DEFAULT
        )

        notificationManager.createNotificationChannel(notificationChannel)
        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}