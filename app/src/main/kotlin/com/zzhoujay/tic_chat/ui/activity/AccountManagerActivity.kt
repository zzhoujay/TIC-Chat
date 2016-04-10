package com.zzhoujay.tic_chat.ui.activity

import android.os.Bundle
import com.zzhoujay.tic_chat.ui.fragment.AccountManagerFragment

/**
 * Created by zhou on 16-4-9.
 */
class AccountManagerActivity : ToolBarActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        swipeBack = true
        super.onCreate(savedInstanceState)
        quickFinish = true

        currFragment = AccountManagerFragment()
    }
}