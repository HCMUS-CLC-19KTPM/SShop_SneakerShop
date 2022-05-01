package com.example.sshop_sneakershop.Notification

import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseInstanceIDService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.e("FCM", "From: " + remoteMessage.notification?.title)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.e("FCM", "Refreshed token: $token")
    }
}