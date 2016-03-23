package com.zzhoujay.tic_chat.ui.activity

import android.os.Bundle
import com.zzhoujay.tic_chat.ui.fragment.LoginFragment

/**
 * Created by zhou on 16-3-23.
 */
class LoginActivity : ToolBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currFragment = LoginFragment()
    }
}