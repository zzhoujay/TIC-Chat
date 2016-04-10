package com.zzhoujay.tic_chat.ui.activity

import android.os.Bundle
import com.zzhoujay.tic_chat.data.Profile
import com.zzhoujay.tic_chat.ui.fragment.ProfileEditorFragment
import com.zzhoujay.tic_chat.util.withArguments

/**
 * Created by zhou on 16-4-10.
 */
class ProfileEditorActivity : ToolBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        swipeBack = true
        super.onCreate(savedInstanceState)
        toolBarTranslate = true
        quickFinish = true

        val fragment = ProfileEditorFragment()
        if (intent.hasExtra(Profile.PROFILE)) {
            fragment.withArguments(Profile.PROFILE to intent.getSerializableExtra(Profile.PROFILE) as Profile)
        }

        currFragment = fragment

    }
}