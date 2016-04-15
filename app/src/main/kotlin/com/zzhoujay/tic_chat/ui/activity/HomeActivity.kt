package com.zzhoujay.tic_chat.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.View
import com.zzhoujay.tic_chat.R
import com.zzhoujay.tic_chat.ui.fragment.BaseFragment
import com.zzhoujay.tic_chat.ui.fragment.MessageFragment
import com.zzhoujay.tic_chat.ui.fragment.ProfileFragment
import com.zzhoujay.tic_chat.ui.fragment.TopicsFragment
import com.zzhoujay.tic_chat.util.checkLogin
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.startActivity

/**
 * Created by zhou on 16-3-24.
 */
class HomeActivity : BaseActivity() {

    companion object {
        const val requestCodeNewTopic = 0x12
        const val requestCodeEditorProfile = 0x13
        const val requestCodeTopicDetail = 0x14

        const val start_flag = "start_flag"

        const val start_topic = 0
        const val start_message = 1
        const val start_profile = 2

        const val option = "option"

        const val option_goto_login = 1
        const val option_exit_app = 2

    }

    val fragments: Array<Fragment> by lazy { arrayOf<Fragment>(TopicsFragment(), MessageFragment(), ProfileFragment()) }
    val tabTitles: Array<String> by lazy { resources.getStringArray(R.array.home_tabs) }

    override fun onCreate(savedInstanceState: Bundle?) {

        checkLogin()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        setTitle(R.string.app_name)

        val adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment? {
                return fragments[position]
            }

            override fun getCount(): Int {
                return fragments.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return tabTitles[position]
            }
        }

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

        viewPager.currentItem = intent.getIntExtra(start_flag, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == requestCodeNewTopic && resultCode == RESULT_OK) {
            (fragments[0] as TopicsFragment).refresh()
        } else if (requestCode == requestCodeEditorProfile && resultCode == RESULT_OK) {
            (fragments[2] as ProfileFragment).refreshUserProfile()
        } else if ( requestCode == requestCodeTopicDetail && resultCode == RESULT_OK) {
            (fragments[0] as TopicsFragment).refresh()
            (fragments[0] as TopicsFragment).gotoTop()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent?.hasExtra(option) ?: false) {
            when (intent?.getIntExtra(option, 0)) {
                option_goto_login -> {
                    startActivity<LoginActivity>(LoginActivity.flag_login to true)
                    finish()
                }
                option_exit_app -> {
                    finish()
                }
            }
        } else if (intent?.hasExtra(start_flag) ?: false) {
            val index = intent?.getIntExtra(start_flag, 0) ?: 0
            viewPager.currentItem = index
            if (index == start_message) {
                (fragments[1] as MessageFragment).refresh()
            }
        }
    }
}