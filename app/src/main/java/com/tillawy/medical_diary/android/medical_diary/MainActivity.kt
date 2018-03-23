package com.tillawy.medical_diary.android.medical_diary

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v4.app.FragmentPagerAdapter



class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_dashboard -> {
                pager.setCurrentItem(0, true)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                pager.setCurrentItem(1, true)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_about -> {
                pager.setCurrentItem(2, true)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupPager()
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    fun setupPager(){

        val fragmentOne = BlankFragment()
        val args1 = Bundle()
        args1.putString("param1", "1")
        fragmentOne.setArguments(args1)

        val fragmentForm = FormFragment()

        val fragmentThree = AboutFragment()

        val pagerAdapter = PagerAdapter(supportFragmentManager)
        pagerAdapter.addFragment(fragmentOne)
        pagerAdapter.addFragment(fragmentForm)
        pagerAdapter.addFragment(fragmentThree)
        pager.setAdapter(pagerAdapter)
    }
}

class PagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

    private val mFragments = ArrayList<Fragment>()

    fun addFragment(fragment: Fragment) {
        mFragments.add(fragment)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }
}