package com.zzhoujay.tic_chat.receiver

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
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
                Log.i("alert", "alert:$alert")
                showNotification(context!!)
            }
        }
        Log.i("receive", "intent:$intent")
    }

    fun showNotification(context: Context) {
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        val intent = Intent(context, HomeActivity::class.java)
        intent.putExtra(HomeActivity.start_flag, HomeActivity.start_message)
        val pi = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val builder = NotificationCompat.Builder(context)
        builder.setTicker("有新消息")
                .setContentTitle("你有一条新的消息")
                .setContentText("点击查看")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pi)
        val n=builder.build()
        n.flags= Notification.FLAG_AUTO_CANCEL
        notificationManager.notify(0, n);
    }
}