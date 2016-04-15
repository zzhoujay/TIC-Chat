package com.zzhoujay.tic_chat.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.data.Profile
import com.zzhoujay.tic_chat.ui.fragment.AvatarCropFragment
import com.zzhoujay.tic_chat.ui.fragment.ProfileEditorFragment
import com.zzhoujay.tic_chat.util.withArguments
import kotlin.properties.Delegates

/**
 * Created by zhou on 16-4-10.
 */
class ProfileEditorActivity : ToolBarActivity() {

    companion object {
        const val REQUEST_CODE_PICK_AVATAR = 11
        const val REQUEST_CODE_CROP_AVATAR = 12
    }

    private var fragment: ProfileEditorFragment by Delegates.notNull<ProfileEditorFragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        swipeBack = true
        super.onCreate(savedInstanceState)
        toolBarBackgroundColor = ContextCompat.getColor(this, R.color.material_lightBlue_500)
        quickFinish = true

        fragment = ProfileEditorFragment()
        if (intent.hasExtra(Profile.PROFILE)) {
            fragment.withArguments(Profile.PROFILE to intent.getSerializableExtra(Profile.PROFILE) as Profile,
                    ProfileEditorFragment.flag_editable to true)
        }
        currFragment = fragment

        title="个人资料"

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_AVATAR && resultCode == RESULT_OK) {
            val intent = Intent(this, AvatarCropActivity::class.java)
            intent.putExtra(AvatarCropFragment.IMAGE_URI, data?.data)
            startActivityForResult(intent, REQUEST_CODE_CROP_AVATAR)
        } else if (requestCode == REQUEST_CODE_CROP_AVATAR && resultCode == RESULT_OK) {
            fragment.updateAvatar(data!!.data)
        }
    }
}