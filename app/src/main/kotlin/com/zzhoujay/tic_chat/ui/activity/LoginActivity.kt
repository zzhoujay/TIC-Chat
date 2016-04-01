package com.zzhoujay.tic_chat.ui.activity

import android.os.Bundle
import com.zzhoujay.tic_chat.ui.fragment.LoginFragment
import com.zzhoujay.tic_chat.ui.fragment.RegisterFragment

/**
 * Created by zhou on 16-3-23.
 */
class LoginActivity : ToolBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currFragment = if (intent.getBooleanExtra(flag_login, false)) LoginFragment() else RegisterFragment()
    }

    companion object {
        const val flag_login = "login"
    }
}