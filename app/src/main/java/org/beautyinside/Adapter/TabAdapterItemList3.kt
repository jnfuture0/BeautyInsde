package org.beautyinside.Adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import org.beautyinside.ListFragment.*

class TabAdapterItemList3(fm:FragmentManager):FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> FragmentList3_1()
            1 -> FragmentList3_2()
            2 -> FragmentList3_3()
            3 -> FragmentList3_4()
            4 -> FragmentList3_5()
            5 -> FragmentList3_6()
            6 -> FragmentList3_7()
            7 -> FragmentList3_8()
            else -> FragmentList3_1()
        }
    }


    override fun getCount() = 8

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return null
    }

}