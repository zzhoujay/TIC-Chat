package com.zzhoujay.tic_chat.ui.activity

import android.os.Bundle
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

/**
 * Created by zhou on 16-3-24.
 */
class HomeActivity : BaseActivity() {

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
    }
}