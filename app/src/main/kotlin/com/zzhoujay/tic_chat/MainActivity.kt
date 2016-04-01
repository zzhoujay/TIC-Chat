package com.zzhoujay.tic_chat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import cn.bmob.v3.BmobInstallation
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.listener.FindListener
import com.zzhoujay.tic_chat.data.Installation
import com.zzhoujay.tic_chat.ui.activity.HomeActivity
import com.zzhoujay.tic_chat.ui.activity.LoginActivity
import com.zzhoujay.tic_chat.util.SimpleFindListener
import com.zzhoujay.tic_chat.util.progress
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BmobUser.logOut(this)

        start.onClick {
                        startActivity<HomeActivity>()
//            progress(false, "...") {
//                val q = BmobQuery<Installation>()
//                q.addWhereEqualTo("installationId", BmobInstallation.getInstallationId(context))
//                q.findObjects(context, SimpleFindListener<Installation>({ code, msg, list ->
//                    dismiss()
//                    Log.i("result", "code:$code,msg:$msg,list:$list")
//                }))
//            }
        }
    }
}
