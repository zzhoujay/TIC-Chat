package com.zzhoujay.tic_chat.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.bmob.v3.BmobUser
import com.bumptech.glide.Glide
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.data.Profile
import com.zzhoujay.tic_chat.ui.activity.HomeActivity
import kotlinx.android.synthetic.main.fragment_account_manager.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.startActivity

/**
 * Created by zhou on 16-4-10.
 */
class AccountManagerFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_account_manager, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profile: Profile = arguments.getSerializable(Profile.PROFILE) as Profile
        Glide.with(this).load(profile.avatar?.getFileUrl(context)).into(avatar)
        name.text = profile.name

        logout.onClick {
            BmobUser.logOut(context)
            context.startActivity<HomeActivity>(HomeActivity.option to HomeActivity.option_goto_login)
        }

        exit.onClick {
            context.startActivity<HomeActivity>(HomeActivity.option to HomeActivity.option_exit_app)
        }
    }
}