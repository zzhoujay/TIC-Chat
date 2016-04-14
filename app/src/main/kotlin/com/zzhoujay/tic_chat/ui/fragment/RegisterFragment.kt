package com.zzhoujay.tic_chat.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.bmob.v3.BmobInstallation
import cn.bmob.v3.BmobUser
import cn.bmob.v3.datatype.BmobPointer
import cn.bmob.v3.listener.SaveListener
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.common.Configuration
import com.zzhoujay.tic_chat.data.Profile
import com.zzhoujay.tic_chat.data.User
import com.zzhoujay.tic_chat.ui.activity.HomeActivity
import com.zzhoujay.tic_chat.ui.activity.ToolBarActivity
import com.zzhoujay.tic_chat.util.*
import kotlinx.android.synthetic.main.fragment_register.*
import org.jetbrains.anko.enabled
import org.jetbrains.anko.onClick

/**
 * Created by zhou on 16-3-23.
 */
class RegisterFragment : BaseFragment() {

    var registerEnable: Boolean
        get() = registerAction.enabled
        set(value) {
            registerAction.enabled = !value
        }
    var accountOk: Boolean = false
        set(value) {
            field = value
            account.isErrorEnabled = !value
            registerEnable = value && emailOk && passwordOk && repeatPasswordOk
        }
    var emailOk: Boolean = false
        set(value) {
            field = value
            email.isErrorEnabled = !value
            registerEnable = value && accountOk && passwordOk && repeatPasswordOk
        }
    var passwordOk: Boolean = false
        set(value) {
            field = value
            password.isErrorEnabled = !value
            registerEnable = value && accountOk && emailOk && repeatPasswordOk
        }
    var repeatPasswordOk: Boolean = false
        set(value) {
            field = value
            repeatPassword.isErrorEnabled = !value
            registerEnable = value && accountOk && emailOk && passwordOk
        }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle(R.string.text_register_title)

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

        repeatPassword.editText!!.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                var a = s?.toString() ?: ""
                repeatPasswordOk = a.equals(password.editText?.text.toString())
                if (!repeatPasswordOk) {
                    repeatPassword.error = getString(R.string.error_password_repeat)
                }
            }
        })

        gotoLogin.onClick {
            notice<ToolBarActivity> {
                currFragment = LoginFragment()
            }
        }

        registerAction.onClick {

            progress(false, getString(R.string.alert_register)) {

                val ac = account.editText!!.text.toString()
                val pd = password.editText!!.text.toString()
                val em = email.editText!!.text.toString()

                val profile = Profile(ac, em)


                profile.save(context, SimpleSaveListener({ code, msg ->
                    if (code == 0) {
                        val user = User(profile )
                        user.username = ac
                        user.setPassword(pd)
                        user.email = em
                        user.signUp(context, SimpleSaveListener({ code, msg ->
                            dismiss()
                            if (code == 0) {
                                toast(R.string.toast_register_success)
                                startActivity<HomeActivity>()
                                finish()
                            } else {
                                toast("code:$code,msg:$msg")
                            }
                        }))
                    } else {
                        dismiss()
                        toast("注册失败")
                    }
                }))

            }

        }
    }
}