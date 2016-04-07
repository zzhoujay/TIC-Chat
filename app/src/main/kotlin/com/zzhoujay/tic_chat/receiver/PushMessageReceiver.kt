package com.zzhoujay.tic_chat.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import cn.bmob.push.PushConstants

/**
 * Created by zhou on 16-3-23.
 */
class PushMessageReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (PushConstants.ACTION_MESSAGE.equals(intent?.action)) {
            val msg = intent!!.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING)


        }
        Log.i("receive", "intent:$intent")
    }
}