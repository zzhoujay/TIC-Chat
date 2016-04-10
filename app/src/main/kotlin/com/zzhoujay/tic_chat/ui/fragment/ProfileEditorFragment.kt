package com.zzhoujay.tic_chat.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.KeyListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.data.Profile
import com.zzhoujay.tic_chat.util.SimpleTextWatcher
import kotlinx.android.synthetic.main.fragment_profile_editor.*
import org.jetbrains.anko.AlertDialogBuilder
import org.jetbrains.anko.alert
import org.jetbrains.anko.onClick
import kotlin.properties.Delegates

/**
 * Created by zhou on 16-4-9.
 */
class ProfileEditorFragment : BaseFragment() {

    var edits: Array<EditText> by Delegates.notNull<Array<EditText>>()
    var keyListener: KeyListener by Delegates.notNull<KeyListener>()
    var useProfile: Profile by Delegates.notNull<Profile>()

    var updateEnable: Boolean = false

    var editable: Boolean = false
        set(value) {
            if (!value) {
                keyListener = edits[0].keyListener
                edits.forEach { it.keyListener = null }
            } else {
                edits.forEach { it.keyListener = keyListener }
            }
        }
    val updateEnableWatch: TextWatcher by lazy {
        object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                super.afterTextChanged(s)
                updateEnable = true
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_profile_editor, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edits = arrayOf(edit_qq, edit_college, edit_email, edit_home, edit_name)
        editable = false

        if (arguments.containsKey(Profile.PROFILE)) {
            useProfile = arguments.getSerializable(Profile.PROFILE) as Profile
            setUpProfile(useProfile)
        }

        edits.forEach { it.addTextChangedListener(updateEnableWatch) }

        edit_sex.onClick { selectSex() }
        edit_age.onClick { selectAge() }
    }

    fun setUpProfile(profile: Profile) {
        edit_qq.setText(profile.qq)
        edit_name.setText(profile.name)
        edit_age.text = "${profile.age}"
        edit_home.setText(profile.home)
        edit_college.setText(profile.college)
        edit_sex.text = Profile.sex(profile.sex)
        edit_email.setText(profile.email)
    }

    fun updateProfile() {
        useProfile.email = edit_email.text.toString()
        useProfile.qq = edit_qq.text.toString()
        useProfile.age = edit_age.text.toString().toInt()
        useProfile.home = edit_home.text.toString()
        useProfile.college = edit_college.text.toString()
        useProfile.name = edit_name.text.toString()
    }

    fun selectSex() {
        context.alert {
            title("请选择性别")
            items(arrayOf<CharSequence>("男", "女", "保密")) {
                useProfile.sex = when (it) {
                    0 -> 1
                    1 -> -1
                    else -> 0
                }
                edit_sex.text = Profile.sex(useProfile.sex)
                updateEnable = true
            }
        }.show()
    }

    fun selectAge() {
        context.alert {
            title("请选择年龄")
            items((0..100).toList().map { "$it" }) {
                useProfile.age = it
                updateEnable = true
                edit_age.text = "$it"
            }
        }.show()
    }


}