package org.beautyinside.ListFragment

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
import kotlinx.android.synthetic.main.fragment_items_list_1_1.*
import org.beautyinside.Adapter.RecyclerAdapterItemList
import org.beautyinside.Board.Board
import org.beautyinside.Board.BoardInner
import org.beautyinside.Board.BoardInnerRealm
import org.beautyinside.MainActivity
import org.beautyinside.R

class FragmentList1_5 : Fragment() {
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
        super.onResume()
        boardList = mutableListOf()
        firstList = mutableListOf()
        secondList = mutableListOf()
        thirdList = mutableListOf()
        fourthList = mutableListOf()
        fifthList = mutableListOf()
        val firstAll = MainActivity.realm.where(BoardInnerRealm::class.java).equalTo("category", "블러셔").findAll()
        for(element in firstAll){
            firstList.add(BoardInner(element.id!!, element.image!!, element.name, element.color, element.capacity, element.price, element.expiryDate, element.etc, element.category, element.brandName, element.storeName, 0))
        }
        val secondAll = MainActivity.realm.where(BoardInnerRealm::class.java).equalTo("category", "쉐딩").findAll()
        for(element in secondAll){
            secondList.add(BoardInner(element.id!!, element.image!!, element.name, element.color, element.capacity, element.price, element.expiryDate, element.etc, element.category, element.brandName, element.storeName, 0))
        }
        val thirdAll = MainActivity.realm.where(BoardInnerRealm::class.java).equalTo("category", "하이라이터").findAll()
        for(element in thirdAll){
            thirdList.add(BoardInner(element.id!!, element.image!!, element.name, element.color, element.capacity, element.price, element.expiryDate, element.etc, element.category, element.brandName, element.storeName, 0))
        }
        val fourthAll = MainActivity.realm.where(BoardInnerRealm::class.java).equalTo("category", "컨투어링팔레트").findAll()
        for(element in fourthAll){
            fourthList.add(BoardInner(element.id!!, element.image!!, element.name, element.color, element.capacity, element.price, element.expiryDate, element.etc, element.category, element.brandName, element.storeName, 0))
        }
        val fifthAll = MainActivity.realm.where(BoardInnerRealm::class.java).equalTo("category", "멀티팔레트").findAll()
        for(element in fifthAll){
            fifthList.add(BoardInner(element.id!!, element.image!!, element.name, element.color, element.capacity, element.price, element.expiryDate, element.etc, element.category, element.brandName, element.storeName, 0))
        }

        boardList.add(Board("블러셔", firstList,0))
        boardList.add(Board("쉐딩",secondList,0))
        boardList.add(Board("하이라이터",thirdList,0))
        boardList.add(Board("컨투어링팔레트",fourthList,0))
        boardList.add(Board("멀티팔레트",fifthList,0))




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
                    "블러셔" -> {
                        firstList.add(finalBoardInner)
                        boardList.removeAt(0)
                        boardList.add(0, Board(categoryString, firstList,0))
                    }
                    "쉐딩"->{
                        secondList.add(finalBoardInner)
                        boardList.removeAt(1)
                        boardList.add(1, Board(categoryString, secondList,0))
                    }
                    "하이라이터" -> {
                        thirdList.add(finalBoardInner)
                        boardList.removeAt(2)
                        boardList.add(2, Board(categoryString, thirdList,0))
                    }
                    "컨투어링팔레트" -> {
                        fourthList.add(finalBoardInner)
                        boardList.removeAt(3)
                        boardList.add(3, Board(categoryString, fourthList,0))
                    }
                    "멀티팔레트" ->{
                        fifthList.add(finalBoardInner)
                        boardList.removeAt(4)
                        boardList.add(4, Board(categoryString, fifthList,0))
                    }
                    else -> {

                    }
                }
                recyclerAdapter.notifyDataSetChanged()
                Toast.makeText(context,"추가되었습니다", Toast.LENGTH_SHORT).show()
            }else if(type == TYPE_CANCEL){
                finalBoardInner = BoardInner(0, "","","","","","","","","","",0)
            }
        }
        fragment_item_list_1_1_recyclerView.adapter = recyclerAdapter
        fragment_item_list_1_1_recyclerView.layoutManager = LinearLayoutManager(context)
    }

    var firstList :MutableList<BoardInner> = mutableListOf()
    var secondList: MutableList<BoardInner> = mutableListOf()
    var thirdList: MutableList<BoardInner> = mutableListOf()
    var fourthList: MutableList<BoardInner> = mutableListOf()
    var fifthList: MutableList<BoardInner> = mutableListOf()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)






    }



}