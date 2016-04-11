package com.zzhoujay.tic_chat

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import cn.bmob.v3.BmobUser
import com.zzhoujay.tic_chat.ui.activity.AvatarCropActivity
import com.zzhoujay.tic_chat.ui.activity.HomeActivity
import com.zzhoujay.tic_chat.ui.activity.LoginActivity
import com.zzhoujay.tic_chat.ui.fragment.AvatarCropFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start.onClick {
            if (BmobUser.getCurrentUser(this) == null) {
                startActivity<LoginActivity>(LoginActivity.flag_login to true)
            } else {
                startActivity<HomeActivity>()
            }
        }
        //        start.onClick {
        //            val i = Intent(Intent.ACTION_GET_CONTENT)
        //            i.type = "image/*"
        //            startActivityForResult(i, 10);
        //        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10 && resultCode == RESULT_OK) {
            val intent = Intent(this, AvatarCropActivity::class.java)
            intent.putExtra(AvatarCropFragment.IMAGE_URI, data?.data)
            startActivityForResult(intent, 11)
        } else if (requestCode == 11 && resultCode == RESULT_OK) {
            Log.i("rrrr", "uri:${data?.data}")
        }
        Log.i("result", "requestCoe:$requestCode,resultCode:$resultCode")
    }
}
