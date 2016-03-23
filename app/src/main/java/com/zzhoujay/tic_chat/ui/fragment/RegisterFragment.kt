package com.zzhoujay.tic_chat.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.common.Configuration
import com.zzhoujay.tic_chat.ui.activity.ToolBarActivity
import com.zzhoujay.tic_chat.util.SimpleTextWatcher
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
                    account.error="请输入合法的账号"
//                    account.error = getString(R.string.error_account_length, Configuration.Profile.minAccount, Configuration.Profile.maxAccount)
                }
            }
        })

        password.editText!!.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                var a = s?.toString() ?: ""
                passwordOk = a.length >= Configuration.Profile.minPassword && a.length <= Configuration.Profile.maxPassword
                if (!accountOk) {
                    password.error="请输入合法的密码"
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

        }
    }
}