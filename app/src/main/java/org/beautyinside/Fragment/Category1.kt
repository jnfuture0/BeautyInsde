package org.beautyinside.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.category1.*
import org.beautyinside.Adapter.TabAdapterItemList1
import org.beautyinside.R

class Category1 : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.category1, container, false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    /*override fun onResume() {
        super.onResume()
        tabAdapter.notifyDataSetChanged()
    }*/

    private val tabAdapter by lazy{TabAdapterItemList1(childFragmentManager)}

    fun setClick(tv:TextView, item:Int){
        tv.setOnClickListener {
            setBtnWhite()
            tv.setBackgroundResource(R.drawable.button_bottom_d2d2d2)
            category1_viewPager.currentItem = item
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setClick(category1_btn1_skinHair, 0)
        setClick(category1_btn2_faceMakeUp, 1)
        setClick(category1_btn3, 2)
        setClick(category1_btn4, 3)
        setClick(category1_btn5, 4)
        setClick(category1_btn6, 5)
        setClick(category1_btn7, 6)
        setClick(category1_btn8, 7)
        setClick(category1_btn9, 8)
        setClick(category1_btn10, 9)
        setClick(category1_btn11, 10)
        setClick(category1_btn12, 11)
        setClick(category1_btn13, 12)
        setClick(category1_btn14, 13)
        setClick(category1_btn15, 14)
        setClick(category1_btn16, 15)
        setClick(category1_btn17, 16)
        /*category1_btn1_skinHair.setOnClickListener {
            setBtnWhite()
            category1_btn1_skinHair.setBackgroundResource(R.drawable.button_bottom_d2d2d2)
            category1_viewPager.currentItem = 0
        }
        category1_btn2_faceMakeUp.setOnClickListener {
            setBtnWhite()
            category1_btn2_faceMakeUp.setBackgroundResource(R.drawable.button_bottom_d2d2d2)
            category1_viewPager.currentItem = 1
        }
        category1_btn3.setOnClickListener {
            setBtnWhite()
            category1_btn3.setBackgroundResource(R.drawable.button_bottom_d2d2d2)
            category1_viewPager.currentItem = 2
        }
        category1_btn4.setOnClickListener {
            setBtnWhite()
            category1_btn4.setBackgroundResource(R.drawable.button_bottom_d2d2d2)
            category1_viewPager.currentItem = 3
        }
        category1_btn5.setOnClickListener {
            setBtnWhite()
            category1_btn5.setBackgroundResource(R.drawable.button_bottom_d2d2d2)
            category1_viewPager.currentItem = 4
        }
        category1_btn6.setOnClickListener {
            setBtnWhite()
            category1_btn6.setBackgroundResource(R.drawable.button_bottom_d2d2d2)
            category1_viewPager.currentItem = 5
        }
        category1_btn7.setOnClickListener {
            setBtnWhite()
            category1_btn7.setBackgroundResource(R.drawable.button_bottom_d2d2d2)
            category1_viewPager.currentItem = 6
        }
        category1_btn8.setOnClickListener {
            setBtnWhite()
            category1_btn8.setBackgroundResource(R.drawable.button_bottom_d2d2d2)
            category1_viewPager.currentItem = 7
        }
        category1_btn9.setOnClickListener {
            setBtnWhite()
            category1_btn9.setBackgroundResource(R.drawable.button_bottom_d2d2d2)
            category1_viewPager.currentItem = 8
        }
        category1_btn10.setOnClickListener {
            setBtnWhite()
            category1_btn10.setBackgroundResource(R.drawable.button_bottom_d2d2d2)
            category1_viewPager.currentItem = 9
        }
        category1_btn11.setOnClickListener {
            setBtnWhite()
            category1_btn11.setBackgroundResource(R.drawable.button_bottom_d2d2d2)
            category1_viewPager.currentItem = 10
        }
        category1_btn12.setOnClickListener {
            setBtnWhite()
            category1_btn12.setBackgroundResource(R.drawable.button_bottom_d2d2d2)
            category1_viewPager.currentItem = 11
        }
        category1_btn13.setOnClickListener {
            setBtnWhite()
            category1_btn13.setBackgroundResource(R.drawable.button_bottom_d2d2d2)
            category1_viewPager.currentItem = 12
        }
        category1_btn14.setOnClickListener {
            setBtnWhite()
            category1_btn14.setBackgroundResource(R.drawable.button_bottom_d2d2d2)
            category1_viewPager.currentItem = 13
        }
        category1_btn15.setOnClickListener {
            setBtnWhite()
            category1_btn15.setBackgroundResource(R.drawable.button_bottom_d2d2d2)
            category1_viewPager.currentItem = 14
        }
        category1_btn16.setOnClickListener {
            setBtnWhite()
            category1_btn16.setBackgroundResource(R.drawable.button_bottom_d2d2d2)
            category1_viewPager.currentItem = 15
        }
        category1_btn17.setOnClickListener {
            setBtnWhite()
            category1_btn17.setBackgroundResource(R.drawable.button_bottom_d2d2d2)
            category1_viewPager.currentItem = 16
        }*/


        category1_viewPager.adapter = tabAdapter
        category1_viewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int,positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {}
        })

    }

    private fun setBtnWhite(){
        category1_btn1_skinHair.setBackgroundResource(R.drawable.button_bottom_bababa)
        category1_btn2_faceMakeUp.setBackgroundResource(R.drawable.button_bottom_bababa)
        category1_btn3.setBackgroundResource(R.drawable.button_bottom_bababa)
        category1_btn4.setBackgroundResource(R.drawable.button_bottom_bababa)
        category1_btn5.setBackgroundResource(R.drawable.button_bottom_bababa)
        category1_btn6.setBackgroundResource(R.drawable.button_bottom_bababa)
        category1_btn7.setBackgroundResource(R.drawable.button_bottom_bababa)
        category1_btn8.setBackgroundResource(R.drawable.button_bottom_bababa)
        category1_btn9.setBackgroundResource(R.drawable.button_bottom_bababa)
        category1_btn10.setBackgroundResource(R.drawable.button_bottom_bababa)
        category1_btn11.setBackgroundResource(R.drawable.button_bottom_bababa)
        category1_btn12.setBackgroundResource(R.drawable.button_bottom_bababa)
        category1_btn13.setBackgroundResource(R.drawable.button_bottom_bababa)
        category1_btn14.setBackgroundResource(R.drawable.button_bottom_bababa)
        category1_btn15.setBackgroundResource(R.drawable.button_bottom_bababa)
        category1_btn16.setBackgroundResource(R.drawable.button_bottom_bababa)
        category1_btn17.setBackgroundResource(R.drawable.button_bottom_bababa)
    }

}
