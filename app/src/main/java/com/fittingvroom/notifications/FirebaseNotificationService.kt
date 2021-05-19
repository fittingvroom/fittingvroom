package com.fittingvroom.notifications

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import com.fittingvroom.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseNotificationService : FirebaseMessagingService(), NotificationService {

    private var messageId = 10;

    //получить токен для тестовой отправки
    override fun getFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result

            // Log
            Log.d(TAG, token.toString())
        })
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        val title = p0.notification?.title ?: "There should have been a title here."
        val text = p0.notification?.body ?: ""

        val builder = NotificationCompat.Builder(this, "2")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentText(text)
            .setContentTitle(title)

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(messageId++, builder.build())
    }

    override fun onNewToken(p0: String) {
        val refreshedToken : String = FirebaseInstallations.getInstance().getToken(true).toString()
        Log.d(TAG, "[Refreshed token: ${refreshedToken} ]")
    }

    companion object {
        private const val TAG = "token_tag"
    }
}