package com.zzhoujay.tic_chat.receiver

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.app.NotificationCompat
import android.util.Log
import cn.bmob.push.PushConstants
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.data.Alert
import com.zzhoujay.tic_chat.ui.activity.HomeActivity

/**
 * Created by zhou on 16-3-23.
 */
class PushMessageReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (PushConstants.ACTION_MESSAGE.equals(intent?.action)) {
            val msg = intent!!.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING)
            Log.i("message", "message:$msg")
            val alert = Alert.fromJson(msg)
            if (alert.type == Alert.type_message) {
                Log.i("alert","alert:$alert")
            }
        }
        Log.i("receive", "intent:$intent")
    }

    fun showNotification(context: Context) {
        val notificationBuilder = NotificationCompat.Builder(context)
        val intent = Intent(context, HomeActivity::class.java)
        intent.putExtra(HomeActivity.start_flag, HomeActivity.start_message)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher).setContentText("你有新消息了").setContentIntent(pendingIntent)
        val notification = notificationBuilder.build()
        val nm = context.getSystemService(NotificationManagerCompat::class.java)
        nm.notify(0x123, notification)
    }
}