package org.beautyinside.Fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.kotlin.createObject
import kotlinx.android.synthetic.main.category2.*
import org.beautyinside.Adapter.RecyclerAdapterItemList
import org.beautyinside.Board.Board
import org.beautyinside.Board.BoardInner
import org.beautyinside.Board.BoardInnerRealm
import org.beautyinside.Board.BrandRealm
import org.beautyinside.MainActivity
import org.beautyinside.R

class Category2 : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.category2, container, false)
    }



    lateinit var recyclerAdapter: RecyclerAdapterItemList
    var boardList:MutableList<Board> = mutableListOf()
    var brandItemList:MutableList<BoardInner> = mutableListOf()
    val emptyItemList:MutableList<BoardInner> = mutableListOf()
    var brandNameList :MutableList<String> = mutableListOf()

    var finalBoardInner:BoardInner = BoardInner(0, "","","","","","","","","","",0)
    val TYPE_IMG_PLUS = 0
    val TYPE_ADD = 1
    val TYPE_CANCEL = 2
    val TYPE_BRAND_PLUS = 3
    val REQUEST_GET_IMAGE = 105
    lateinit var userAddImg : ImageView
    lateinit var imgPlusBtn : Button


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

    override fun onResume() {
        super.onResume()
        boardList = mutableListOf()
        brandItemList = mutableListOf()
        brandNameList = mutableListOf()


        val brandAll = MainActivity.realm.where(BrandRealm::class.java).findAll()
        for(element in brandAll){
            brandNameList.add(element.brandName!!)
        }

        for(element in brandNameList){
            val brandElementAll = MainActivity.realm.where(BoardInnerRealm::class.java).equalTo("brandName", element).findAll()
            brandItemList = mutableListOf()
            for(element2 in brandElementAll){
                brandItemList.add(BoardInner(element2.id!!, element2.image!!, element2.name, element2.color, element2.capacity, element2.price, element2.expiryDate, element2.etc, element2.category, element2.brandName, element2.storeName, 2))//no_brand_type
            }
            boardList.add(Board(element, brandItemList, 2))
        }
        boardList.add(Board("",emptyItemList,1)) //add_brand


        recyclerAdapter = RecyclerAdapterItemList(context!!,boardList,layoutInflater){imgView, plusBtn, boardInner, boardName, type ->
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
                finalBoardInner.brandName = boardName
                finalBoardInner.storeName = boardInner.storeName
                finalBoardInner.category = boardInner.category // 카테고리

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
                newObject.category = finalBoardInner.category
                realm.commitTransaction()
                //추가할때 처리.
                for(i in 0 until brandNameList.size){
                    if(boardName == brandNameList[i]){
                        var boardInnerList:MutableList<BoardInner>  = boardList[i].boardContentList
                        boardInnerList.add(finalBoardInner)
                        boardList.removeAt(i)
                        boardList.add(i, Board(boardName, boardInnerList, 2))
                    }
                }
                recyclerAdapter.notifyDataSetChanged()
                Toast.makeText(context,"추가되었습니다", Toast.LENGTH_SHORT).show()
            }else if(type == TYPE_CANCEL){
                finalBoardInner = BoardInner(0, "","","","","","","","","","",0)
            }else if(type == TYPE_BRAND_PLUS){
                boardList.removeAt(boardList.size-1)
                boardList.add(Board(boardName!!, emptyItemList, 2))
                boardList.add(Board("",emptyItemList,1))
                recyclerAdapter.notifyDataSetChanged()
            }
        }
        category2_recyclerView.adapter = recyclerAdapter
        category2_recyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }


}
