package org.beautyinside.Adapter

import android.app.AlertDialog
import android.content .Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import io.realm.kotlin.createObject
import kotlinx.android.synthetic.main.form_add_brand.view.*
import kotlinx.android.synthetic.main.form_board.view.*
import org.beautyinside.Board.Board
import org.beautyinside.Board.BoardInner
import org.beautyinside.Board.BoardInnerRealm
import org.beautyinside.Board.BrandRealm
import org.beautyinside.MainActivity
import org.beautyinside.R

class RecyclerAdapterItemList(val context: Context, val boardList : MutableList<Board>, layoutInflater: LayoutInflater, val itemClick:(ImageView?, Button?, BoardInner?,String?, Int) -> Unit) : RecyclerView.Adapter<RecyclerAdapterItemList.BaseViewHolder<*>>() {
    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }
    private fun showHide(view:View){
        if(view.visibility == View.GONE){
            view.visibility = View.VISIBLE
        }else{
            view.visibility = View.GONE
        }
    }

    val layoutInflaterInner = layoutInflater

    val TYPE_IMG_PLUS = 0
    val TYPE_ADD = 1
    val TYPE_CANCEL = 2
    val TYPE_BRAND_PLUS = 3
    val TYPE_NO_CATEGORY_SPINNER = 100
    val TYPE_NO_BRAND_SPINNER = 101
    val TYPE_NO_STORE_SPINNER = 102
    var getBrandPosition = 0
    var storeSelected = "미정"
    var brandSelected = "미정"
    var categorySelected = "미정"
    var brandItems : MutableList<String> = mutableListOf("미정")

    private fun setCategorySpinner(spinner:Spinner){
        val categoryItems :MutableList<String> = mutableListOf(
            "스킨/토너","로션/에멀젼","에센스/세럼","크림","미스트","페이스오일","파운데이션","피니시파우더",
            "컨실러","베이스/프라이머","BB크림","CC크림","메이크업픽서","립스틱","틴트/라커","립펜슬","립글로즈","립베이스",
            "립케어","아이섀도우","아이라이너","아이브로우","마스카라","아이프라이머","블러셔","쉐딩","하이라이터","컨투어링팔레트",
            "멀티팔레트","브러시","스펀지/퍼프","메이크업소품","페이셜클렌저","메이크업클렌저","포인트리무버","각질케어","클렌징도구",
            "마스크","선크림","선스틱","선젤","선스프레이","바디로션/크림","바디오일/밤","바디미스트","바디슬리밍","데오드란트","바디마사지",
            "샴푸","린스/컨디셔너","트리트먼트","스타일링","폴리시","베이스/탑코트","네일스티커","페디스티커","네일케어","네일리무버","네일도구",
            "여성용품","위생용품","컬러렌즈","투명렌즈","렌즈용품","향수","방향제","뷰티디바이스","헤어디바이스","피부건강","다이어트","건강식품"
        )
        val spinnerAdapter = object:ArrayAdapter<String>(context, R.layout.form_spinner_text){}
        spinnerAdapter.addAll(categoryItems)
        spinner.adapter = spinnerAdapter
        //spinner.setSelection(spinnerAdapter.count)
        spinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(p2 != categoryItems.size) {
                    categorySelected = categoryItems[p2]
                    Log.e("CHECK_SPINNER",categorySelected)
                }
            }
        }
    }
    private fun setStoreSpinner(spinner:Spinner){
        val storeItems :MutableList<String> = mutableListOf(
            "미정",
            "올리브영","롭스","아리따움","랄라블라",
            "눙크","세포라","시코르","네이처컬렉션")
        val spinnerAdapter = object:ArrayAdapter<String>(context, R.layout.form_spinner_text){
            /*override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent)
                return v
            }*/
        }
        spinnerAdapter.addAll(storeItems)
        spinner.adapter = spinnerAdapter
        //spinner.setSelection(spinnerAdapter.count)
        spinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(p2 != storeItems.size) {
                    storeSelected = storeItems[p2]
                }
            }
        }
    }
    private fun setBrandSpinner(spinner:Spinner){
        val brandAll = MainActivity.realm.where(BrandRealm::class.java).findAll()
        brandItems = mutableListOf("미정")
        for(element in brandAll){
            brandItems.add(element.brandName.toString())
        }
        val spinnerAdapter = object:ArrayAdapter<String>(context, R.layout.form_spinner_text){
            /*override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val v = super.getView(position, convertView, parent)
                return v
            }*/
        }
        spinnerAdapter.addAll(brandItems)
        spinner.adapter = spinnerAdapter
        //spinner.setSelection(spinnerAdapter.count)
        spinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(p2 != brandItems.size) {
                    brandSelected = brandItems[p2]
                }
            }
        }
    }



    private fun setRecyclerAdapter(recycler:RecyclerView, innerList : MutableList<BoardInner>, boardName:String, addWhat:Int){

        val recyclerAdapter = RecyclerAdapterItemList_Inner(context, innerList, layoutInflaterInner, boardName){
            //add content
            val builderAddContent = AlertDialog.Builder(context)
            val dialogViewAddContent = layoutInflaterInner.inflate(R.layout.dialog_add_item, null)
            val dialogImageView = dialogViewAddContent.findViewById<ImageView>(R.id.dialog_img_add_imgView)
            val dialogImgPlusBtn = dialogViewAddContent.findViewById<Button>(R.id.dialog_img_add_plus_btn)
            val dialogAddBtn = dialogViewAddContent.findViewById<Button>(R.id.dialog_item_add_btn)
            val dialogCancelBtn = dialogViewAddContent.findViewById<Button>(R.id.dialog_item_cancel_btn)
            val addName = dialogViewAddContent.findViewById<TextView>(R.id.dialog_item_name)
            val addColor = dialogViewAddContent.findViewById<TextView>(R.id.dialog_item_color)
            val addCapacity = dialogViewAddContent.findViewById<TextView>(R.id.dialog_item_capacity)
            val addPrice = dialogViewAddContent.findViewById<TextView>(R.id.dialog_item_price)
            val addExpiryDate = dialogViewAddContent.findViewById<TextView>(R.id.dialog_item_expiryDate)
            val addEtc = dialogViewAddContent.findViewById<TextView>(R.id.dialog_item_etc)
            val categorySpinner = dialogViewAddContent.findViewById<Spinner>(R.id.dialog_item_category_spinner)
            val categorySpinnerLayout = dialogViewAddContent.findViewById<LinearLayout>(R.id.dialog_item_category_spinner_layout)
            val brandSpinner = dialogViewAddContent.findViewById<Spinner>(R.id.dialog_item_brand_spinner)
            val brandSpinnerLayout = dialogViewAddContent.findViewById<LinearLayout>(R.id.dialog_item_brand_spinner_layout)
            val storeSpinner = dialogViewAddContent.findViewById<Spinner>(R.id.dialog_item_store_spinner)
            val storeSpinnerLayout = dialogViewAddContent.findViewById<LinearLayout>(R.id.dialog_item_store_spinner_layout)
            builderAddContent.setView(dialogViewAddContent)
            val add :AlertDialog = builderAddContent.create()

            when (addWhat) {
                TYPE_NO_CATEGORY_SPINNER -> {
                    setStoreSpinner(storeSpinner)
                    setBrandSpinner(brandSpinner)
                    categorySpinnerLayout.visibility = View.GONE
                }
                TYPE_NO_BRAND_SPINNER -> {
                    setCategorySpinner(categorySpinner)
                    setStoreSpinner(storeSpinner)
                    brandSpinnerLayout.visibility = View.GONE
                }
                TYPE_NO_STORE_SPINNER -> {
                    setCategorySpinner(categorySpinner)
                    setBrandSpinner(brandSpinner)
                    storeSpinnerLayout.visibility = View.GONE
                }
            }

            add.show()

            //이미지 추가하기 버튼
            dialogImgPlusBtn.setOnClickListener {
                itemClick(dialogImageView, dialogImgPlusBtn,null,null,TYPE_IMG_PLUS)
            }
            //이미지 다시 클릭했을 때
            dialogImageView.setOnClickListener {
                itemClick(dialogImageView, dialogImgPlusBtn,null,null,TYPE_IMG_PLUS)
            }


            dialogAddBtn.setOnClickListener {
                if(dialogImgPlusBtn.visibility != View.VISIBLE) {
                    var boardInner :BoardInner
                    var realm = MainActivity.realm
                    val currentId = realm.where<BoardInnerRealm>(BoardInnerRealm::class.java).max("id")
                    val nextId = if(currentId == null) 1 else currentId.toInt() + 1
                    when(addWhat){
                        TYPE_NO_CATEGORY_SPINNER -> {
                            boardInner = BoardInner(
                                nextId.toLong(), dialogImageView.tag as String, addName.text.toString(), addColor.text.toString(), addCapacity.text.toString(),
                                addPrice.text.toString(), addExpiryDate.text.toString(), addEtc.text.toString(), boardName, brandSelected, storeSelected, 2)
                        }
                        TYPE_NO_BRAND_SPINNER -> {
                            boardInner = BoardInner(
                                nextId.toLong(),dialogImageView.tag as String, addName.text.toString(), addColor.text.toString(), addCapacity.text.toString(),
                                addPrice.text.toString(), addExpiryDate.text.toString(), addEtc.text.toString(), categorySelected, boardName, storeSelected, 2)
                        }
                        TYPE_NO_STORE_SPINNER -> {
                            boardInner = BoardInner(
                                nextId.toLong(),dialogImageView.tag as String, addName.text.toString(), addColor.text.toString(), addCapacity.text.toString(),
                                addPrice.text.toString(), addExpiryDate.text.toString(), addEtc.text.toString(), categorySelected, brandSelected, boardName, 2)
                        }
                        else -> {
                            boardInner = BoardInner(
                                nextId.toLong(),dialogImageView.tag as String, addName.text.toString(), addColor.text.toString(), addCapacity.text.toString(),
                                addPrice.text.toString(), addExpiryDate.text.toString(), addEtc.text.toString(), categorySelected, brandSelected, storeSelected, 2)
                        }
                    }

                    add.dismiss()
                    itemClick(null,null,boardInner,boardName, TYPE_ADD)
                }else{
                    Toast.makeText(context,"사진을 첨부해주세요",Toast.LENGTH_SHORT).show()
                }
            }

            dialogCancelBtn.setOnClickListener {
                add.dismiss()
                itemClick(null,null,null,boardName, TYPE_CANCEL)
            }

        }
        recycler.adapter = recyclerAdapter
        recycler.layoutManager = LinearLayoutManager(context)
    }


    //no_category_type
    inner class ViewHolderNoCategory(itemView: View, parent:ViewGroup):BaseViewHolder<Board>(itemView){
        val boardCategoryName = itemView.form_board_category_name
        val boardRecyclerView = itemView.form_board_recyclerView

        override fun bind(board: Board) {
            boardCategoryName.text = board.boardName             //카테고리 텍스트 추가

            val boardListInAdapter : MutableList<BoardInner> = mutableListOf()
            for(element in board.boardContentList){
                boardListInAdapter.add(BoardInner(element.id, element.image, element.name,element.color, element.capacity, element.price, element.expiryDate, element.etc,
                    element.category, element.brandName, element.storeName, TYPE_NO_CATEGORY_SPINNER))
            }
            boardListInAdapter.add(BoardInner(-1, "","","","","","","","","","",1))               // +버튼 추가
            setRecyclerAdapter(boardRecyclerView, boardListInAdapter, board.boardName, TYPE_NO_CATEGORY_SPINNER)       // 어뎁터 추가 (재귀)
            boardCategoryName.setOnClickListener {                          // 누르면 아래 리사이클러뷰 show
                showHide(boardRecyclerView)
            }
        }
    }

    inner class ViewHolderNoBrand(itemView: View, parent:ViewGroup):BaseViewHolder<Board>(itemView){
        val boardName = itemView.form_board_category_name
        val boardRecyclerView = itemView.form_board_recyclerView

        override fun bind(board: Board) {
            boardName.text = board.boardName             //카테고리 텍스트 추가

            val boardListInAdapter : MutableList<BoardInner> = mutableListOf()
            for(element in board.boardContentList){
                boardListInAdapter.add(BoardInner(element.id, element.image, element.name,element.color, element.capacity, element.price, element.expiryDate, element.etc,
                    element.category, element.brandName, element.storeName, TYPE_NO_BRAND_SPINNER))
            }
            boardListInAdapter.add(BoardInner(-1, "","","","","","","","","","",1))               // +버튼 추가
            setRecyclerAdapter(boardRecyclerView, boardListInAdapter, board.boardName, TYPE_NO_BRAND_SPINNER)       // 어뎁터 추가 (재귀)
            boardName.setOnClickListener {                          // 누르면 아래 리사이클러뷰 show
                showHide(boardRecyclerView)
            }
        }
    }

    inner class ViewHolderNoStore(itemView: View, parent:ViewGroup):BaseViewHolder<Board>(itemView){
        val boardCategoryName = itemView.form_board_category_name
        val boardRecyclerView = itemView.form_board_recyclerView

        override fun bind(board: Board) {
            boardCategoryName.text = board.boardName             //카테고리 텍스트 추가

            val boardListInAdapter : MutableList<BoardInner> = mutableListOf()
            for(element in board.boardContentList){
                boardListInAdapter.add(BoardInner(element.id, element.image, element.name,element.color, element.capacity, element.price, element.expiryDate, element.etc,
                    element.category, element.brandName, element.storeName, TYPE_NO_STORE_SPINNER))
            }
            boardListInAdapter.add(BoardInner(-1, "","","","","","","","","","",1))               // +버튼 추가
            setRecyclerAdapter(boardRecyclerView, boardListInAdapter, board.boardName, TYPE_NO_STORE_SPINNER)       // 어뎁터 추가 (재귀)
            boardCategoryName.setOnClickListener {                          // 누르면 아래 리사이클러뷰 show
                showHide(boardRecyclerView)
            }
        }
    }


    //브랜드 종류 추가할 때
    inner class ViewHolder2(itemView: View, parent:ViewGroup):BaseViewHolder<Board>(itemView){
        val addBtn = itemView.form_add_board_plus_btn

        override fun bind(board: Board) {
            addBtn.setOnClickListener {
                val builderAddContent = AlertDialog.Builder(context)
                val dialogViewAddContent = layoutInflaterInner.inflate(R.layout.dialog_add_brand, null)
                val brandName = dialogViewAddContent.findViewById<TextInputEditText>(R.id.dialog_add_brand_brand_name)
                val brandAddBtn = dialogViewAddContent.findViewById<Button>(R.id.dialog_add_brand_add_btn)
                val brandCancelBtn = dialogViewAddContent.findViewById<Button>(R.id.dialog_add_brand_cancel_btn)
                builderAddContent.setView(dialogViewAddContent)
                val add :AlertDialog = builderAddContent.create()
                add.show()

                brandAddBtn.setOnClickListener {
                    if(brandName.text.toString() != "" && brandName.text!!.isNotEmpty()){
                        var realm = MainActivity.realm
                        realm.beginTransaction()
                        val currentId = realm.where<BrandRealm>(BrandRealm::class.java).max("id")
                        val nextId = if(currentId == null) 1 else currentId.toInt() + 1
                        val newObject = realm.createObject<BrandRealm>(nextId)
                        newObject.brandName = brandName.text.toString()
                        realm.commitTransaction()
                        Toast.makeText(context, "추가되었습니다",Toast.LENGTH_SHORT).show()
                        add.dismiss()
                        itemClick(null,null,null,brandName.text.toString(),TYPE_BRAND_PLUS)
                    }else{
                        Toast.makeText(context, "브랜드 이름을 입력해주세요",Toast.LENGTH_SHORT).show()
                    }
                }

                brandCancelBtn.setOnClickListener {
                    add.dismiss()
                }
            }
        }
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when(viewType){
            0 -> {
                val view = LayoutInflater.from(context).inflate(R.layout.form_board, parent, false)
                ViewHolderNoCategory(view, parent)
            }
            1 -> {
                val view = LayoutInflater.from(context).inflate(R.layout.form_add_brand, parent, false)
                ViewHolder2(view, parent)
            }
            2 -> {
                val view = LayoutInflater.from(context).inflate(R.layout.form_board, parent, false)
                ViewHolderNoBrand(view, parent)
            }
            3 -> {
                val view = LayoutInflater.from(context).inflate(R.layout.form_board, parent, false)
                ViewHolderNoStore(view, parent)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = boardList[position]


        when(holder){
            is ViewHolderNoCategory -> holder.bind(element as Board)
            is ViewHolder2 -> holder.bind(element as Board)
            is ViewHolderNoBrand -> holder.bind(element as Board)
            is ViewHolderNoStore -> holder.bind(element as Board)
            else -> throw IllegalArgumentException()
        }
    }


    override fun getItemViewType(position: Int): Int {
        return boardList[position].type
    }

    override fun getItemCount(): Int {
        return boardList.size
    }
}