package com.zzhoujay.tic_chat.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.bmob.v3.BmobInstallation
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.datatype.BmobPointer
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.LogInListener
import cn.bmob.v3.listener.UpdateListener
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.common.Configuration
import com.zzhoujay.tic_chat.data.Installation
import com.zzhoujay.tic_chat.data.User
import com.zzhoujay.tic_chat.ui.activity.HomeActivity
import com.zzhoujay.tic_chat.ui.activity.ToolBarActivity
import com.zzhoujay.tic_chat.util.*
import kotlinx.android.synthetic.main.fragment_login.*
import org.jetbrains.anko.enabled
import org.jetbrains.anko.onClick

/**
 * Created by zhou on 16-3-23.
 */
class LoginFragment : BaseFragment() {

    var loginEnable: Boolean
        get() = loginAction.enabled
        set(value) {
            loginAction.enabled = value
        }
    var accountOk: Boolean = false
        set(value) {
            field = value
            account.isErrorEnabled = !value
            loginEnable = value && passwordOk
        }
    var passwordOk: Boolean = false
        set(value) {
            field = value
            password.isErrorEnabled = !value
            loginEnable = value && accountOk
        }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle(R.string.text_login_title)

        account.editText!!.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                var a = s?.toString() ?: ""
                accountOk = a.length >= Configuration.Profile.minAccount && a.length <= Configuration.Profile.maxAccount
                if (!accountOk) {
                    account.error = "请输入合法的账号"
                    //                    account.error = getString(R.string.error_account_length, Configuration.Profile.minAccount, Configuration.Profile.maxAccount)
                }
            }
        })

        password.editText!!.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                var a = s?.toString() ?: ""
                passwordOk = a.length >= Configuration.Profile.minPassword && a.length <= Configuration.Profile.maxPassword
                if (!accountOk) {
                    password.error = "请输入合法的密码"
                    //                    account.error = getString(R.string.error_password_length, Configuration.Profile.minPassword, Configuration.Profile.maxPassword)
                }
            }
        })

        gotoRegister.onClick {
            notice<ToolBarActivity> {
                currFragment = RegisterFragment()
            }
        }

        loginAction.onClick {
            val ac = account.editText!!.text.toString()
            val pd = password.editText!!.text.toString()

            progress(false, getString(R.string.alert_login)) {

                BmobUser.loginByAccount(context, ac, pd, object : LogInListener<User>() {
                    override fun done(p0: User?, p1: BmobException?) {
                        if (p0 == null) {
                            dismiss()
                            toast("登陆失败")
                        } else {
                            setMessage(getString(R.string.alert_please_wait))
                            val query = BmobQuery<Installation>()
                            query.addWhereEqualTo("installationId", BmobInstallation.getInstallationId(context))
                            query.findObjects(context, object : FindListener<Installation>() {
                                override fun onSuccess(list: MutableList<Installation>?) {
                                    val installation = list!![0]
                                    installation.setValue("user", BmobPointer(p0))
                                    installation.update(context, SimpleUpdateListener({ code, msg ->
                                        dismiss()
                                        if (code == 0) {
                                            startActivity<HomeActivity>()
                                            finish()
                                        } else {
                                            toast("code:$code,msg:$msg")
                                        }
                                    }))
                                }

                                override fun onError(code: Int, msg: String?) {
                                    dismiss()
                                    toast("code:$code,msg:$msg")
                                }
                            })
                        }
                    }
                })
            }
        }

    }

}