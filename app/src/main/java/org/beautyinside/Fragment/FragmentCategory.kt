package org.beautyinside.Fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_fragment_category.*
import org.beautyinside.Adapter.TabAdapterCategory
import org.beautyinside.R
import java.lang.Exception
import java.lang.NullPointerException

class FragmentCategory : Fragment() {

    private val adapter by lazy{TabAdapterCategory(childFragmentManager)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_fragment_category, container, false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        vpCategory.adapter = FragmentCenter@adapter
        vpCategory.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int,positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                when(position){
                    0 -> { }
                    1 -> { }
                }
            }
        })

        tabLayout_category_page.setupWithViewPager(vpCategory)
        tabLayout_category_page.getTabAt(0)?.text = "분류"
        tabLayout_category_page.getTabAt(1)?.text = "브랜드"
        tabLayout_category_page.getTabAt(2)?.text = "스토어"
        tabLayout_category_page.setTabTextColors(Color.parseColor("#bfbfbf"), Color.parseColor("#001023"))

    }
}
