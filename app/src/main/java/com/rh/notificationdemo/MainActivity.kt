package com.rh.notificationdemo

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val channelID = "com.rh.notificationdemo.channel"
    private var notificationManager: NotificationManager? = null
    private val KEY_REPLy = "key_reply"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(channelID, name = "DemoChannel", channelDesc = "This is Demo")


        button.setOnClickListener {
            displayNotification()


        }
    }


    private fun displayNotification(){
        val notificationId = 45
        val tapResultIntent = Intent(this, SecondActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
                this,
                0,
                tapResultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )

        //Reply Action
        val remoteInput : RemoteInput = RemoteInput.Builder(KEY_REPLy).run {
            setLabel("Insert youe name here")
            build()
        }

        val replyAction: NotificationCompat.Action = NotificationCompat.Action.Builder(
                0,
                "REPLY",
                pendingIntent
        ).addRemoteInput(remoteInput).build()


        //Action Button 2
        val intent2 = Intent(this, DetailsActivity::class.java)
        val pendingIntent2: PendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent2,
                PendingIntent.FLAG_UPDATE_CURRENT
        )
        val action2 : NotificationCompat.Action =
                NotificationCompat.Action.Builder(0,"Details", pendingIntent2).build()

        //Action Button 3
        val intent3 = Intent(this, SetingsActivity::class.java)
        val pendingIntent3: PendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent3,
                PendingIntent.FLAG_UPDATE_CURRENT
        )
        val action3 : NotificationCompat.Action =
                NotificationCompat.Action.Builder(0,"Settings", pendingIntent3).build()



/*
        val notification: Notification? = NotificationCompat.Builder(this@MainActivity,channelID)
                .setContentTitle("Demo Title")
                .setContentText("This is a Demo notification")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .addAction(action2)
                .addAction(action3)
                .build()*/

        val notification: Notification? = NotificationCompat.Builder(this@MainActivity,channelID)
                .setContentTitle("Demo Title")
                .setContentText("This is a Demo notification")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .addAction(action2)
                .addAction(action3)
                .addAction(replyAction)
                .build()

        notificationManager?.notify(notificationId,notification)

    }

    private fun createNotificationChannel(id:String, name:String, channelDesc: String){

        if ((Build.VERSION.SDK_INT>= Build.VERSION_CODES.O)){
            val importance = NotificationManager.IMPORTANCE_HIGH
            val  channel = NotificationChannel(id, name, importance).apply {
               description =channelDesc
            }
            notificationManager?.createNotificationChannel(channel)

        }

    }
}