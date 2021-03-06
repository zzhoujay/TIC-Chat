package com.zzhoujay.tic_chat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.bmob.v3.BmobUser
import com.zzhoujay.tic_chat.data.User
import com.zzhoujay.tic_chat.ui.activity.HomeActivity
import com.zzhoujay.tic_chat.ui.activity.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start.postDelayed({
            if (BmobUser.getCurrentUser(this, User::class.java) != null) {
                startActivity<HomeActivity>()
            } else {
                startActivity<LoginActivity>(LoginActivity.flag_login to true)
            }
            finish()
        }, 1500)

    }

}
