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

        gotoRegister.onClick {
            notice<ToolBarActivity> {
                currFragment = RegisterFragment()
            }
        }

        loginAction.onClick {

        }

    }

}