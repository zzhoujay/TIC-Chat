package com.zzhoujay.tic_chat

import android.Manifest
import android.app.Application
import cn.bmob.push.BmobPush
import cn.bmob.v3.Bmob
import cn.bmob.v3.BmobInstallation
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.zzhoujay.tic_chat.common.ApiKey
import org.jetbrains.anko.toast
import kotlin.properties.Delegates

/**
 * Created by zhou on 16-3-23.
 */
class App : Application() {

    companion object {
        var app: App by Delegates.notNull<App>()
    }

    override fun onCreate() {
        super.onCreate()
        app = this

        Dexter.initialize(this)

        Dexter.checkPermission(object : PermissionListener {
            override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                initBmob()
            }

            override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
            }

            override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                toast("Permission Denied")
            }

        }, Manifest.permission.READ_PHONE_STATE)


    }

    fun initBmob() {
        Bmob.initialize(this, ApiKey.apiKey)
        BmobInstallation.getCurrentInstallation(this).save()
        BmobPush.startWork(this, ApiKey.apiKey)
    }
}