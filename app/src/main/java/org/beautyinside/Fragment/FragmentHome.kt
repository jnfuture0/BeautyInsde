package org.beautyinside.Fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_fragment_home.*
import org.beautyinside.Adapter.RecyclerAdapterItemList_Inner
import org.beautyinside.Board.BoardInner
import org.beautyinside.Board.BoardInnerRealm
import org.beautyinside.Intro
import org.beautyinside.MainActivity
import org.beautyinside.R
import java.lang.Exception
import java.lang.NullPointerException

class FragmentHome : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_fragment_home, container, false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    lateinit var innerAdapter :RecyclerAdapterItemList_Inner
    val firstList :MutableList<BoardInner> = mutableListOf()
    lateinit var firstAll : RealmResults<BoardInnerRealm>
    override fun onResume() {
        super.onResume()
        innerAdapter.notifyDataSetChanged()
        if(firstAll.size != 0){
            home_no_info_text.visibility = View.GONE
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        firstAll = MainActivity.realm.where(BoardInnerRealm::class.java).findAll()
        for(element in firstAll){
            firstList.add(BoardInner(element.id!!, element.image!!, element.name, element.color, element.capacity, element.price, element.expiryDate, element.etc, element.category, element.brandName, element.storeName, 0))
        }
        if(firstAll.size == 0){
            home_no_info_text.visibility = View.VISIBLE
        }


        innerAdapter = RecyclerAdapterItemList_Inner(context!!, firstList, layoutInflater,""){}

        home_recyclerView.adapter = innerAdapter
        home_recyclerView.layoutManager = LinearLayoutManager(context)
    }

}
