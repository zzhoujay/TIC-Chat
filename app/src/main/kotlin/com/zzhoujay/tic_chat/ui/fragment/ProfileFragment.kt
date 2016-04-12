package com.zzhoujay.tic_chat.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.listener.GetListener
import com.bumptech.glide.Glide
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.data.Profile
import com.zzhoujay.tic_chat.data.User
import com.zzhoujay.tic_chat.ui.activity.AccountManagerActivity
import com.zzhoujay.tic_chat.ui.activity.ProfileEditorActivity
import com.zzhoujay.tic_chat.util.loading
import com.zzhoujay.tic_chat.util.startActivity
import com.zzhoujay.tic_chat.util.toast
import kotlinx.android.synthetic.main.fragment_profile.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.startActivity
import kotlin.properties.Delegates

/**
 * Created by zhou on 16-3-24.
 */
class ProfileFragment : BaseFragment() {

    var useProfile: Profile?=null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profile.onClick {
            if(useProfile==null){
                toast("正在加载账号信息中。。。")
            }else{
                startActivity<ProfileEditorActivity>(Profile.PROFILE to useProfile!!)
            }
        }

        account_manager.onClick {
            if(useProfile==null){
                toast("正在加载账号信息中。。。")
            }else{
                startActivity<AccountManagerActivity>(Profile.PROFILE to useProfile!!)
            }
        }

        swipeRefreshLayout.setOnRefreshListener { refreshUserProfile() }

        post { refreshUserProfile() }
    }

    fun setUpUserProfile(profile: Profile) {
        useProfile = profile
        Glide.with(this).load(profile.avatar?.getFileUrl(context)).into(icon)
        name.text = profile.name
    }

    fun refreshUserProfile() {
        loading(swipeRefreshLayout) {
            val query = BmobQuery<User>()
            query.include("profile")
            query.getObject(context, BmobUser.getObjectByKey(context, "objectId") as String, object : GetListener<User>() {
                override fun onSuccess(p0: User?) {
                    isRefreshing = false
                    if (p0 != null)
                        setUpUserProfile(p0.profile)
                }

                override fun onFailure(p0: Int, p1: String?) {
                    isRefreshing = false
                    Log.i("refreshUserProfile", "code:$p0,msg:$p1")
                }
            })
        }
    }
}