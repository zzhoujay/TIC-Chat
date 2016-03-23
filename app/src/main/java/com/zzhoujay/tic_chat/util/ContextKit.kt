package com.zzhoujay.tic_chat.util

import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast

/**
 * Created by zhou on 16-3-23.
 */


fun Fragment.toast(msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(res: Int) {
    Toast.makeText(context, res, Toast.LENGTH_SHORT).show()
}

open class SimpleTextWatcher: TextWatcher {
    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}