package com.thanatos.app.base

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


/**
 * 该类内的每一个生成的 Fragment 都将保存在内存之中，
 * 因此适用于那些相对静态的页，数量也比较少的那种；
 * 如果需要处理有很多页，并且数据动态性较大、占用内存较多的情况，
 * 应该使用FragmentStatePagerAdapter。
 */
class BaseFragmentAdapter : androidx.fragment.app.FragmentPagerAdapter {

    private var fragmentList: List<androidx.fragment.app.Fragment>? = ArrayList()
    private var mTitles: List<String>? = null

    constructor(fm: androidx.fragment.app.FragmentManager, fragmentList: List<androidx.fragment.app.Fragment>) : super(fm) {
        this.fragmentList = fragmentList
    }

    constructor(fm: androidx.fragment.app.FragmentManager, fragmentList: List<androidx.fragment.app.Fragment>, mTitles: List<String>) : super(fm) {
        this.mTitles = mTitles
        setFragments(fm, fragmentList, mTitles)
    }

    //刷新fragment
    @SuppressLint("CommitTransaction")
    private fun setFragments(fm: androidx.fragment.app.FragmentManager, fragments: List<androidx.fragment.app.Fragment>, mTitles: List<String>) {
        this.mTitles = mTitles
        if (this.fragmentList != null) {
            val ft = fm.beginTransaction()
            fragmentList?.forEach {
                ft.remove(it)
            }
            ft.commitAllowingStateLoss()
            fm.executePendingTransactions()
        }
        this.fragmentList = fragments
        notifyDataSetChanged()
    }

    override fun getPageTitle(position: Int): CharSequence {
        return if (null != mTitles) mTitles!![position] else ""
    }

    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        return fragmentList!![position]
    }

    override fun getCount(): Int {
        return fragmentList!!.size
    }

}