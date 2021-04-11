package org.beautyinside.Adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import org.beautyinside.ListFragment.*

class TabAdapterItemList1(fm:FragmentManager):FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> FragmentList1_1()
            1 -> FragmentList1_2()
            2 -> FragmentList1_3()
            3 -> FragmentList1_4()
            4 -> FragmentList1_5()
            5 -> FragmentList1_6()
            6 -> FragmentList1_7()
            7 -> FragmentList1_8()
            8 -> FragmentList1_9()
            9 -> FragmentList1_10()
            10 -> FragmentList1_11()
            11 -> FragmentList1_12()
            12 -> FragmentList1_13()
            13 -> FragmentList1_14()
            14 -> FragmentList1_15()
            15 -> FragmentList1_16()
            16 -> FragmentList1_17()
            else -> FragmentList1_1()
        }
    }


    override fun getCount() = 17

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return null
    }

}