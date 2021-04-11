package org.beautyinside.ListFragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.kotlin.createObject
import kotlinx.android.synthetic.main.fragment_items_list_1_1.*
import org.beautyinside.Adapter.RecyclerAdapterItemList
import org.beautyinside.Board.Board
import org.beautyinside.Board.BoardInner
import org.beautyinside.Board.BoardInnerRealm
import org.beautyinside.Fragment.Category1
import org.beautyinside.MainActivity
import org.beautyinside.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

class FragmentList1_1 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        containerViewGroup = container!!
        return inflater.inflate(R.layout.fragment_items_list_1_1, container, false)
    }

    lateinit var containerViewGroup :ViewGroup
    val TYPE_IMG_PLUS = 0
    val TYPE_ADD = 1
    val TYPE_CANCEL = 2


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                REQUEST_GET_IMAGE -> {
                    try{
                        var uri = data?.data
                        finalBoardInner.image = uri.toString()
                        userAddImg.setImageURI(uri)
                        userAddImg.tag = uri.toString()

                        imgPlusBtn.visibility = View.GONE
                    }catch (e:Exception){}
                }
            }
        }
    }

    var boardList:MutableList<Board> = mutableListOf()
    var finalBoardInner:BoardInner = BoardInner(0, "","","","","","","","","","",0)
    val REQUEST_GET_IMAGE = 105
    lateinit var userAddImg :ImageView
    lateinit var imgPlusBtn : Button
    lateinit var recyclerAdapter:RecyclerAdapterItemList


    override fun onResume() {
        init()
        recyclerAdapter = RecyclerAdapterItemList(requireContext(), boardList, layoutInflater){ imgView, plusBtn, boardInner, categoryString, type ->
            if(type == TYPE_IMG_PLUS) {
                val intent = Intent(Intent.ACTION_PICK)
                userAddImg = imgView!!
                imgPlusBtn = plusBtn!!
                intent.type = "image/*"
                startActivityForResult(intent, REQUEST_GET_IMAGE)
            }else if(type == TYPE_ADD){
                finalBoardInner.name = boardInner?.name!!
                finalBoardInner.color = boardInner?.color!!
                finalBoardInner.capacity = boardInner?.capacity!!
                finalBoardInner.price = boardInner?.price!!
                finalBoardInner.expiryDate = boardInner?.expiryDate!!
                finalBoardInner.etc= boardInner?.etc!!
                finalBoardInner.brandName = boardInner?.brandName
                finalBoardInner.storeName = boardInner?.storeName
                finalBoardInner.category = boardInner?.category

                //DB에 저장
                var realm = MainActivity.realm
                realm.beginTransaction()
                val currentId = realm.where<BoardInnerRealm>(BoardInnerRealm::class.java).max("id")
                val nextId = if(currentId == null) 1 else currentId.toInt() + 1
                val newObject = realm.createObject<BoardInnerRealm>(nextId)
                newObject.image = finalBoardInner.image
                newObject.name = finalBoardInner.name
                newObject.color = finalBoardInner.color
                newObject.capacity= finalBoardInner.capacity
                newObject.price = finalBoardInner.price
                newObject.expiryDate = finalBoardInner.expiryDate
                newObject.etc = finalBoardInner.etc
                newObject.brandName = finalBoardInner.brandName
                newObject.storeName = finalBoardInner.storeName
                newObject.category = categoryString
                realm.commitTransaction()
                when(categoryString){
                    "스킨/토너" -> {
                        skinList.add(finalBoardInner)
                        boardList.removeAt(0)
                        boardList.add(0, Board(categoryString, skinList,0))
                    }
                    "로션/에멀젼"->{
                        lotionList.add(finalBoardInner)
                        boardList.removeAt(1)
                        boardList.add(1, Board(categoryString, lotionList,0))
                    }
                    "에센스/세럼" -> {
                        esenseList.add(finalBoardInner)
                        boardList.removeAt(2)
                        boardList.add(2, Board(categoryString, esenseList,0))
                    }
                    "크림" -> {
                        creamList.add(finalBoardInner)
                        boardList.removeAt(3)
                        boardList.add(3, Board(categoryString, creamList,0))
                    }
                    "미스트" ->{
                        meastList.add(finalBoardInner)
                        boardList.removeAt(4)
                        boardList.add(4, Board(categoryString, meastList,0))
                    }
                    "페이스오일" -> {
                        oilList.add(finalBoardInner)
                        boardList.removeAt(5)
                        boardList.add(5, Board(categoryString, oilList,0))
                    }
                    else->{}
                }
                recyclerAdapter.notifyDataSetChanged()
                Toast.makeText(context,"추가되었습니다",Toast.LENGTH_SHORT).show()
            }else if(type == TYPE_CANCEL){
                finalBoardInner = BoardInner(0, "","","","","","","","","","",0)
            }
        }
        fragment_item_list_1_1_recyclerView.adapter = recyclerAdapter
        fragment_item_list_1_1_recyclerView.layoutManager = LinearLayoutManager(context)
        super.onResume()
    }

    var skinList :MutableList<BoardInner> = mutableListOf()
    var lotionList: MutableList<BoardInner> = mutableListOf()
    var esenseList: MutableList<BoardInner> = mutableListOf()
    var creamList: MutableList<BoardInner> = mutableListOf()
    var meastList: MutableList<BoardInner> = mutableListOf()
    var oilList: MutableList<BoardInner> = mutableListOf()


    fun init(){
        skinList = mutableListOf()
        lotionList = mutableListOf()
        esenseList = mutableListOf()
        creamList = mutableListOf()
        meastList = mutableListOf()
        oilList = mutableListOf()
        boardList = mutableListOf()

        val skinAll = MainActivity.realm.where(BoardInnerRealm::class.java).equalTo("category", "스킨/토너").findAll()
        for(element in skinAll){
            skinList.add(BoardInner(element.id!!, element.image!!, element.name, element.color, element.capacity, element.price, element.expiryDate, element.etc, element.category, element.brandName, element.storeName, 0))
        }
        val lotionAll = MainActivity.realm.where(BoardInnerRealm::class.java).equalTo("category", "로션/에멀젼").findAll()
        for(element in lotionAll){
            lotionList.add(BoardInner(element.id!!, element.image!!, element.name, element.color, element.capacity, element.price, element.expiryDate, element.etc, element.category, element.brandName, element.storeName, 0))
        }
        val esenseAll = MainActivity.realm.where(BoardInnerRealm::class.java).equalTo("category", "에센스/세럼").findAll()
        for(element in esenseAll){
            esenseList.add(BoardInner(element.id!!, element.image!!, element.name, element.color, element.capacity, element.price, element.expiryDate, element.etc, element.category, element.brandName, element.storeName, 0))
        }
        val creamAll = MainActivity.realm.where(BoardInnerRealm::class.java).equalTo("category", "크림").findAll()
        for(element in creamAll){
            creamList.add(BoardInner(element.id!!, element.image!!, element.name, element.color, element.capacity, element.price, element.expiryDate, element.etc, element.category, element.brandName, element.storeName, 0))
        }
        val meastAll = MainActivity.realm.where(BoardInnerRealm::class.java).equalTo("category", "미스트").findAll()
        for(element in meastAll){
            meastList.add(BoardInner(element.id!!, element.image!!, element.name, element.color, element.capacity, element.price, element.expiryDate, element.etc, element.category, element.brandName, element.storeName, 0))
        }
        val oilAll = MainActivity.realm.where(BoardInnerRealm::class.java).equalTo("category", "페이스오일").findAll()
        for(element in oilAll){
            oilList.add(BoardInner(element.id!!, element.image!!, element.name, element.color, element.capacity, element.price, element.expiryDate, element.etc, element.category, element.brandName, element.storeName, 0))
        }

        boardList.add(Board("스킨/토너", skinList,0))
        boardList.add(Board("로션/에멀젼",lotionList,0))
        boardList.add(Board("에센스/세럼",esenseList,0))
        boardList.add(Board("크림",creamList,0))
        boardList.add(Board("미스트",meastList,0))
        boardList.add(Board("페이스오일",oilList,0))
    }




    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



    }



}