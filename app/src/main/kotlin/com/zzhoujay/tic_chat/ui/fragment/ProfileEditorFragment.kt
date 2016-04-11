package com.zzhoujay.tic_chat.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.KeyListener
import android.view.*
import android.widget.EditText
import cn.bmob.v3.datatype.BmobFile
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.data.Profile
import com.zzhoujay.tic_chat.util.SimpleTextWatcher
import com.zzhoujay.tic_chat.util.SimpleUpdateListener
import com.zzhoujay.tic_chat.util.progress
import com.zzhoujay.tic_chat.util.toast
import kotlinx.android.synthetic.main.fragment_profile_editor.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.onClick
import java.io.File
import kotlin.properties.Delegates

/**
 * Created by zhou on 16-4-9.
 */
class ProfileEditorFragment : BaseFragment() {

    companion object {
        const val status_before = 0
        const val status_on = 1
        const val status_after = 2
        const val status_updating = 3
    }

    var edits: Array<EditText> by Delegates.notNull<Array<EditText>>()
    var keyListener: KeyListener? = null
    var useProfile: Profile by Delegates.notNull<Profile>()

    var editStatus: Int = status_before
        set(value) {
            if (value == status_before) {
                editActionMenu?.setIcon(R.drawable.ic_create_black_24dp)
            } else if (value == status_after) {
                editActionMenu?.setIcon(R.drawable.ic_done_black_24dp)
            } else {
                editActionMenu?.icon = null
            }
            field = value
        }

    var editTextEditable: Boolean = false
        set(value) {
            if (!value) {
                keyListener = edits[0].keyListener
                edits.forEach { it.keyListener = null }
            } else {
                if (keyListener != null)
                    edits.forEach { it.keyListener = keyListener }
            }
            edit_age.isClickable = value
            edit_sex.isClickable = value
            avatar.isClickable=value
            field = value
        }

    var editable: Boolean = false
        set(value) {
            editActionMenu?.isVisible = value
            field = value
        }
    val updateEnableWatch: TextWatcher by lazy {
        object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                super.afterTextChanged(s)
                editStatus = status_after
            }
        }
    }
    var editActionMenu: MenuItem? = null
    var selectIcon: BmobFile? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_profile_editor, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edits = arrayOf(edit_qq, edit_college, edit_email, edit_home, edit_name)

        editTextEditable = false

        if (arguments.containsKey(Profile.PROFILE)) {
            useProfile = arguments.getSerializable(Profile.PROFILE) as Profile
            setUpProfile(useProfile)
        }

        edits.forEach { it.addTextChangedListener(updateEnableWatch) }

        edit_sex.onClick { selectSex() }
        edit_age.onClick { selectAge() }

        avatar.onClick { selectIcon() }

        editable = true
    }

    fun setUpProfile(profile: Profile) {
        edit_qq.setText(profile.qq)
        edit_name.setText(profile.name)
        edit_age.text = "${profile.age}"
        edit_home.setText(profile.home)
        edit_college.setText(profile.college)
        edit_sex.text = Profile.sex(profile.sex ?: 0)
        edit_email.setText(profile.email)
    }

    fun updateProfile() {

        val tempProfile = Profile()
        tempProfile.email = edit_email.text.toString()
        tempProfile.qq = edit_qq.text.toString()
        tempProfile.age = edit_age.text.toString().toInt()
        tempProfile.home = edit_home.text.toString()
        tempProfile.college = edit_college.text.toString()
        tempProfile.name = edit_name.text.toString()
        tempProfile.avatar = selectIcon
        tempProfile.objectId = useProfile.objectId

        progress(false, "正在更新资料") {
            useProfile.update(context, useProfile.objectId, SimpleUpdateListener({ code, msg ->
                dismiss()
                editStatus = status_before
                var profile: Profile =
                        if (code != 0) {
                            toast("code:$code,msg:$msg")
                            useProfile
                        } else {
                            tempProfile
                        }
                setUpProfile(profile)
            }))
        }
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
                edit_sex.text = Profile.sex(useProfile.sex ?: 0)
                editStatus = status_after
            }
        }.show()
    }

    fun selectAge() {
        context.alert {
            title("请选择年龄")
            items((0..100).toList().map { "$it" }) {
                useProfile.age = it
                editStatus = status_after
                edit_age.text = "$it"
            }
        }.show()
    }

    fun selectIcon() {
        val i = Intent(Intent.ACTION_GET_CONTENT)
        i.type = "image/*"
        activity.startActivityForResult(i, 0);
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.action_profile_editor, menu)
        editActionMenu = menu!!.findItem(R.id.action_profile_editor)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_profile_editor && editable) {
            when (editStatus) {
                status_before -> {
                    editTextEditable = true
                    editStatus = status_on
                }
                status_after -> {
                    updateProfile()
                    editStatus = status_updating
                    editTextEditable = false
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        editActionMenu?.isVisible = editable
        if (editStatus == status_before) {
            editActionMenu?.setIcon(R.drawable.ic_create_black_24dp)
        } else if (editStatus == status_after) {
            editActionMenu?.setIcon(R.drawable.ic_done_black_24dp)
        } else {
            editActionMenu?.icon = null
        }
    }
}