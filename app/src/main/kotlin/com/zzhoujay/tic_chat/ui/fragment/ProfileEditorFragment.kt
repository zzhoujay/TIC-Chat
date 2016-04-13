package com.zzhoujay.tic_chat.ui.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.KeyListener
import android.view.*
import android.widget.EditText
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.datatype.BmobFile
import cn.bmob.v3.listener.GetListener
import com.bumptech.glide.Glide
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.data.Profile
import com.zzhoujay.tic_chat.data.User
import com.zzhoujay.tic_chat.ui.activity.ProfileEditorActivity
import com.zzhoujay.tic_chat.util.*
import kotlinx.android.synthetic.main.fragment_profile_editor.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.onClick
import java.io.File
import kotlin.properties.Delegates

/**
 * Created by zhou on 16-4-9.
 * Profile编辑界面
 */
class ProfileEditorFragment : BaseFragment() {

    companion object {
        const val status_before = 0 // 点击编辑按钮之前
        const val status_on = 1 // 点击了编辑按钮但还没开始编辑
        const val status_after = 2 // 已经编辑了内容
        const val status_updating = 3 // 编辑完成正在更新

        const val flag_editable = "editable"
    }

    // 各种editText
    var edits: Array<EditText> by Delegates.notNull<Array<EditText>>()
    // editText的keyListener
    var keyListener: KeyListener? = null
    // 使用的profile
    var useProfile: Profile by Delegates.notNull<Profile>()
    // 当前编辑的状态
    var editStatus: Int = status_before
        set(value) {
            editActionMenu?.setIcon(when (value) {
                status_on -> {
                    editTextEditable = true
                    R.drawable.ic_clear_black_24dp
                }
                status_after -> {
                    editTextEditable = true
                    R.drawable.ic_done_black_24dp
                }
                status_updating -> {
                    editTextEditable = false
                    R.drawable.ic_cloud_upload_black_24dp
                }
                else -> {
                    editTextEditable = false
                    R.drawable.ic_create_black_24dp
                }
            })
            field = value
        }
    // 各种EditText是否可编辑
    var editTextEditable: Boolean = false
        set(value) {
            if (!value) {
                keyListener = edits[0].keyListener
                edits.forEach {
                    it.keyListener = null
                    it.isFocusable = false
                    ViewKit.hideSoftInput(it)
                }
            } else {
                if (keyListener != null)
                    edits.forEach {
                        it.keyListener = keyListener
                        it.isFocusableInTouchMode = true
                    }
            }
            edit_age.isClickable = value
            edit_sex.isClickable = value
            avatar.isClickable = value
            field = value
        }
    // 当前profile是否可编辑
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

        edits = arrayOf(edit_qq, edit_college, edit_email, edit_home, edit_name, edit_introduction)

        swipeRefreshLayout.isRefreshing = true

        if (try {
            arguments.containsKey(Profile.PROFILE)
        } catch(e: Exception) {
            false
        }) {
            useProfile = arguments.getSerializable(Profile.PROFILE) as Profile
            setUpProfile(useProfile)
        } else {
            refreshProfile()
        }

        editable = try {
            arguments.getBoolean(flag_editable, false)
        } catch(e: Exception) {
            false
        }

        edits.forEach { it.addTextChangedListener(updateEnableWatch) }

        edit_sex.onClick { selectSex() }
        edit_age.onClick { selectAge() }

        avatar.onClick { selectAvatar() }

        swipeRefreshLayout.setOnRefreshListener { refreshProfile(useProfile) }

        ViewKit.setSwipeRefreshLayoutColor(swipeRefreshLayout)

        post { editStatus = status_before }
    }

    /**
     * 刷新profile
     */
    fun refreshProfile(profile: Profile? = null) {
        loading(swipeRefreshLayout) {
            if (profile == null) {
                val query = BmobQuery<User>()
                query.include("profile")
                query.getObject(context, BmobUser.getObjectByKey(context, "objectId") as String, object : GetListener<User>() {
                    override fun onSuccess(p0: User?) {
                        isRefreshing = false
                        if (p0 != null) {
                            useProfile = p0.profile
                            setUpProfile(useProfile)
                            editable = true
                        }
                    }

                    override fun onFailure(p0: Int, p1: String?) {
                        isRefreshing = false
                    }
                })
            } else {
                val query = BmobQuery<Profile>()
                query.getObject(context, profile.objectId, object : GetListener<Profile>() {
                    override fun onSuccess(p0: Profile?) {
                        isRefreshing = false
                        if (p0 != null) {
                            useProfile = p0
                            setUpProfile(useProfile)
                        }
                    }

                    override fun onFailure(p0: Int, p1: String?) {
                        isRefreshing = false
                    }
                })
            }
        }
    }

    /**
     * 设置profile
     */
    fun setUpProfile(profile: Profile) {
        edit_qq.setText(profile.qq)
        val name = profile.name
        edit_name.setText(name)
        val age = "${profile.age}"
        edit_age.text = age
        val home = profile.home
        edit_home.setText(home)
        edit_college.setText(profile.college)
        val sex = Profile.sex(profile.sex)
        edit_sex.text = sex
        edit_email.setText(profile.email)
        edit_introduction.setText(profile.introduction)

        simple_profile.text = "$sex $age $home"
        simple_name.text = name

        Glide.with(this).load(profile.avatar?.getFileUrl(context)).into(avatar)

        editStatus = status_before
        swipeRefreshLayout.isRefreshing = false
    }

    /**
     * 更新profile
     */
    fun updateProfile() {
        useProfile.setValue("email", edit_email.text.toString())
        useProfile.setValue("qq", edit_qq.text.toString())
        useProfile.setValue("age", edit_age.text.toString().toInt())
        useProfile.setValue("home", edit_home.text.toString())
        useProfile.setValue("college", edit_college.text.toString())
        useProfile.setValue("name", edit_name.text.toString())
        useProfile.setValue("introduction", edit_introduction.text.toString())
        if (selectIcon != null)
            useProfile.setValue("avatar", selectIcon)

        progress(false, "正在更新资料") {
            useProfile.update(context, SimpleUpdateListener({ code, msg ->
                dismiss()
                if (code != 0) {
                    toast("更新资料失败")
                } else {
                    activity.setResult(Activity.RESULT_OK)
                }
                editStatus = status_before
                refreshProfile(useProfile)
            }))
        }
    }

    /**
     * 更新头像
     */
    fun updateAvatar(avatarUri: Uri) {
        if (editStatus == status_on || editStatus == status_after) {
            val tempFile = BmobFile(File(avatarUri.path))
            progress(false, "正在上传头像") {
                tempFile.uploadblock(context, SimpleUploadFileListener({ code, msg ->
                    if (code == 0) {
                        dismiss()
                        selectIcon = tempFile
                        Glide.with(this@ProfileEditorFragment).load(avatarUri).into(avatar)
                        editStatus = status_after
                    } else {
                        dismiss()
                        toast("头像上传失败")
                    }
                }))
            }
        }
    }

    /**
     * 选择性别
     */
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

    /**
     * 选择年龄
     */
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

    /**
     * 选择头像
     */
    fun selectAvatar() {
        val i = Intent(Intent.ACTION_GET_CONTENT)
        i.type = "image/*"
        activity.startActivityForResult(i, ProfileEditorActivity.REQUEST_CODE_PICK_AVATAR);
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
                    editStatus = status_on
                }
                status_after -> {
                    updateProfile()
                    editStatus = status_updating
                }
                status_on -> {
                    editStatus = status_before
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        editable = editable
        editStatus = editStatus
    }
}