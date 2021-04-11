package org.beautyinside.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.category3.*
import org.beautyinside.Adapter.TabAdapterItemList3
import org.beautyinside.R

class Category3 : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.category3, container, false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun setClick(tv: TextView, item:Int){
        tv.setOnClickListener {
            setBtnWhite()
            tv.setBackgroundResource(R.drawable.button_bottom_d2d2d2)
            category3_viewPager.currentItem = item
        }
    }
    private fun setBtnWhite(){
        category3_btn1.setBackgroundResource(R.drawable.button_bottom_bababa)
        category3_btn2.setBackgroundResource(R.drawable.button_bottom_bababa)
        category3_btn3.setBackgroundResource(R.drawable.button_bottom_bababa)
        category3_btn4.setBackgroundResource(R.drawable.button_bottom_bababa)
        category3_btn5.setBackgroundResource(R.drawable.button_bottom_bababa)
        category3_btn6.setBackgroundResource(R.drawable.button_bottom_bababa)
        category3_btn7.setBackgroundResource(R.drawable.button_bottom_bababa)
        category3_btn8.setBackgroundResource(R.drawable.button_bottom_bababa)
    }

    private val tabAdapter by lazy{ TabAdapterItemList3(childFragmentManager) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setClick(category3_btn1, 0)
        setClick(category3_btn2, 1)
        setClick(category3_btn3, 2)
        setClick(category3_btn4, 3)
        setClick(category3_btn5, 4)
        setClick(category3_btn6, 5)
        setClick(category3_btn7, 6)
        setClick(category3_btn8, 7)

        category3_viewPager.adapter = tabAdapter
        category3_viewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int,positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {}
        })

    }


}
