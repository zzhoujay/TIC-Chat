package com.zzhoujay.tic_chat.ui.activity

import android.os.Bundle
import com.zzhoujay.tic_chat.data.Profile
import com.zzhoujay.tic_chat.ui.fragment.AccountManagerFragment
import com.zzhoujay.tic_chat.util.withArguments

/**
 * Created by zhou on 16-4-9.
 */
class AccountManagerActivity : ToolBarActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        swipeBack = true
        super.onCreate(savedInstanceState)
        quickFinish = true

        val fragment = AccountManagerFragment()
        if (intent.hasExtra(Profile.PROFILE)) {
            fragment.withArguments(Profile.PROFILE to intent.getSerializableExtra(Profile.PROFILE) as Profile)
        }

        currFragment = fragment

        title="账号管理"
    }
}