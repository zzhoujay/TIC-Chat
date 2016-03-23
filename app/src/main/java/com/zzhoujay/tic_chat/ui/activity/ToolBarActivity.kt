package com.zzhoujay.tic_chat.ui.activity

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.zzhoujay.tic_chat.R
import kotlinx.android.synthetic.main.activity_tool_bar.*

/**
 * Created by zhou on 16-3-23.
 */
open class ToolBarActivity : BaseActivity() {

    var quickFinish = false
        set(value) {
            field = value
            supportActionBar?.setDisplayHomeAsUpEnabled(value)
        }
    var currFragment: Fragment? = null
        set(value) {
            if (currFragment == null) {
                supportFragmentManager.beginTransaction().add(R.id.container, value).commit()
            } else {
                supportFragmentManager.beginTransaction().replace(R.id.container, value).commitAllowingStateLoss()
            }
            field = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tool_bar)

        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayShowTitleEnabled(true)

//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            shadow.background = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, intArrayOf(R.color.colorPrimaryLogin, android.R.color.transparent))
//        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (quickFinish && item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}