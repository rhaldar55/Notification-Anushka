package com.rh.notificationdemo

import android.app.NotificationManager
import android.app.RemoteInput
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {
    private val channelID = "com.rh.notificationdemo.channel"
    val notificationId = 45

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        receiveInput()
    }

    private fun receiveInput(){
         val KEY_REPLy = "key_reply"

        val intent: Intent? = this.intent
        val remoteInput : Bundle? = RemoteInput.getResultsFromIntent(intent)
        if (remoteInput!=null){
            val inputString : String = remoteInput.getCharSequence(KEY_REPLy).toString()

            resultTextView.text = inputString


             val channelID = "com.rh.notificationdemo.channel"
            val notificationId = 45

            val replyNotification = NotificationCompat.Builder(this, channelID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentText("Your reply receive")
                .build()

            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(notificationId,replyNotification)

        }

    }
}