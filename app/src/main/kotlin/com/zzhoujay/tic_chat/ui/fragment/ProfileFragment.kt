package com.zzhoujay.tic_chat.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.listener.GetListener
import com.bumptech.glide.Glide
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.data.Feedback
import com.zzhoujay.tic_chat.data.Profile
import com.zzhoujay.tic_chat.data.User
import com.zzhoujay.tic_chat.ui.activity.AccountManagerActivity
import com.zzhoujay.tic_chat.ui.activity.HomeActivity
import com.zzhoujay.tic_chat.ui.activity.ProfileEditorActivity
import com.zzhoujay.tic_chat.util.SimpleTextWatcher
import com.zzhoujay.tic_chat.util.loading
import com.zzhoujay.tic_chat.util.startActivity
import com.zzhoujay.tic_chat.util.toast
import kotlinx.android.synthetic.main.fragment_profile.*
import org.jetbrains.anko.*
import kotlin.properties.Delegates

/**
 * Created by zhou on 16-3-24.
 */
class ProfileFragment : BaseFragment() {

    var useProfile: Profile? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profile.onClick {
            if (useProfile == null) {
                toast("正在加载账号信息中。。。")
            } else {
                val intent = Intent(context, ProfileEditorActivity::class.java)
                intent.putExtra(Profile.PROFILE, useProfile)
                activity.startActivityForResult(intent, HomeActivity.requestCodeEditorProfile)
            }
        }

        account_manager.onClick {
            if (useProfile == null) {
                toast("正在加载账号信息中。。。")
            } else {
                startActivity<AccountManagerActivity>(Profile.PROFILE to useProfile!!)
            }
        }

        info.onClick { showInfo() }

        setting.onClick { toast("呵呵") }

        reportBug.onClick { reportBug() }

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

    fun showInfo() {
        activity.alert {
            title("关于")
            message("TIC Chat by zzhoujay")
            positiveButton("关闭") { dismiss() }
        }.show()
    }

    fun reportBug() {
        var editText: EditText? = null
        activity.alert {
            title("Bug反馈")
            customView {
                frameLayout {
                    lparams {
                        padding = dip(20)
                    }
                    editText = editText {
                        hint = "请详细描叙你发现的bug，不少于10个字符"
                        minLines = 5
                        gravity = Gravity.START
                    }
                }
            }
            positiveButton("发送") {
                if (editText?.text?.length ?: 0 > 10) {
                    val feedback = Feedback(BmobUser.getCurrentUser(context, User::class.java), editText?.text?.toString() ?: "")
                    feedback.save(context)
                    toast("反馈已发出")
                } else {
                    toast("请输入有效的反馈")
                }
            }
            negativeButton("取消")
        }.show()
    }

}