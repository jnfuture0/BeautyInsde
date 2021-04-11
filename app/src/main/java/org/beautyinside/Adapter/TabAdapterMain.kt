package org.beautyinside.Adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import org.beautyinside.Fragment.Category1
import org.beautyinside.Fragment.FragmentCategory
import org.beautyinside.Fragment.FragmentHome
import org.beautyinside.Fragment.FragmentMenual

class TabAdapterMain(fm:FragmentManager):FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> FragmentHome()
            1 -> FragmentCategory()
            2 -> FragmentMenual()
            else -> FragmentHome()
        }
    }


    override fun getCount() = 3

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return null
    }

}