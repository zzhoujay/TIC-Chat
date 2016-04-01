package com.zzhoujay.tic_chat.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.bmob.v3.BmobUser
import com.bumptech.glide.Glide
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.data.User
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * Created by zhou on 16-3-24.
 */
class ProfileFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = BmobUser.getCurrentUser(context, User::class.java)
        val profile = user.profile
        Glide.with(this).load(profile?.avatar?.getFileUrl(context)).into(icon)

        name.text = profile?.name

    }
}