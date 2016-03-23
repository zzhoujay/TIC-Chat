package com.zzhoujay.tic_chat.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zzhoujay.tic_chat.R
import kotlinx.android.synthetic.main.fragment_login.*
import org.jetbrains.anko.onClick

/**
 * Created by zhou on 16-3-23.
 */
class LoginFragment : BaseFragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        gotoRegister.onClick {
            noticeToolBarActivity {
                currFragment = RegisterFragment()
            }
        }
    }

}