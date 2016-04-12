package com.zzhoujay.tic_chat.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.zzhoujay.tic_chat.R
import kotlinx.android.synthetic.main.activity_tool_bar.*
import org.jetbrains.anko.backgroundColor

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
    private var containerMarginTop: Int = 0
    var toolBarTranslate: Boolean = false
        set(value) {
            val lp = container.layoutParams as CoordinatorLayout.LayoutParams
            if (true) {
                if (lp.topMargin != 0) containerMarginTop = lp.topMargin
                lp.topMargin = 0
                toolBar.backgroundColor = Color.TRANSPARENT
            } else {
                if (containerMarginTop != 0)
                    lp.topMargin = containerMarginTop
            }
        }
    var toolBarBackgroundColor: Int
        get() = toolBar.backgroundColor
        set(value) {
            toolBar.backgroundColor = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tool_bar)

        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (quickFinish && item?.itemId == android.R.id.home) {
            setResult(RESULT_CANCELED)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}