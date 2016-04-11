package com.zzhoujay.tic_chat.ui.activity

import android.os.Bundle
import com.zzhoujay.tic_chat.ui.fragment.AvatarCropFragment
import com.zzhoujay.tic_chat.ui.fragment.AvatarCropFragment.Companion.IMAGE_URI

/**
 * Created by zhou on 16-4-11.
 */
class AvatarCropActivity : ToolBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        swipeBack = true
        super.onCreate(savedInstanceState)
        quickFinish = true

        val fragment = AvatarCropFragment()
        val bundle = Bundle()
        bundle.putParcelable(IMAGE_URI, intent.getParcelableExtra(IMAGE_URI))
        fragment.arguments = bundle
        currFragment = fragment
    }
}