package org.beautyinside.Adapter

import android.app.AlertDialog
import android.content .Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.form_inner_item_board.view.*
import kotlinx.android.synthetic.main.form_inner_plus_btn.view.*
import org.beautyinside.Board.BoardInner
import org.beautyinside.Board.BoardInnerRealm
import org.beautyinside.Board.BrandRealm
import org.beautyinside.MainActivity
import org.beautyinside.R

class RecyclerAdapterItemList_Inner(val context: Context, val boardList : MutableList<BoardInner>, layoutInflater: LayoutInflater, val getBoardName:String, val itemClick:(BoardInner) -> Unit) : RecyclerView.Adapter<RecyclerAdapterItemList_Inner.BaseViewHolder<*>>() {
    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }

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
        val spinnerAdapter = object:ArrayAdapter<String>(context, R.layout.form_spinner_text){}
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
    val layoutInflaterInner = layoutInflater


    inner class ViewHolderNoCategory(itemView: View):BaseViewHolder<BoardInner>(itemView){
        val boardImage = itemView.form_inner_board_image
        val boardName = itemView.form_inner_board_name
        val boardColor = itemView.form_inner_board_color
        val boardCapacity = itemView.form_inner_board_capacity
        val boardPrice = itemView.form_inner_board_price
        val boardExpiryDate = itemView.form_inner_board_expiryDate
        val boardBrand = itemView.form_inner_board_brandName
        val boardStore = itemView.form_inner_board_storeName
        val boardEtc = itemView.form_inner_board_etc
        val boardDeleteBtn = itemView.form_inner_board_delete_btn
        override fun bind(board: BoardInner) {
            if(board.image != "") {
                val uri = Uri.parse(board.image)
                boardImage.setImageURI(uri)
            }
            boardName?.text = "제품명 : " + board.name
            boardColor?.text = "컬러 : " + board.color
            boardCapacity?.text = "용량 : " + board.capacity
            boardPrice?.text = "가격 : " + board.price
            boardExpiryDate.text = "기한 : " + board.expiryDate
            boardBrand.text = "브랜드 : " + board.brandName
            boardStore.text = "스토어 : " + board.storeName
            boardEtc?.text = "메모 : " + board.etc

            var realm = MainActivity.realm

            boardDeleteBtn.setOnClickListener {
                val build = AlertDialog.Builder(context)
                val mOrDdialogView = layoutInflaterInner.inflate(R.layout.dialog_modify_or_delete, null)
                val mBtn = mOrDdialogView.findViewById<Button>(R.id.dialog_m_or_d_modify_btn)
                val dBtn = mOrDdialogView.findViewById<Button>(R.id.dialog_m_or_d_delete_btn)
                build.setView(mOrDdialogView)
                val mordAlert :AlertDialog = build.create()
                mordAlert.show()

                mBtn.setOnClickListener {
                    mordAlert.dismiss()

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
                    val categorySpinnerLayout = dialogViewAddContent.findViewById<LinearLayout>(R.id.dialog_item_category_spinner_layout)
                    val brandSpinner = dialogViewAddContent.findViewById<Spinner>(R.id.dialog_item_brand_spinner)
                    val storeSpinner = dialogViewAddContent.findViewById<Spinner>(R.id.dialog_item_store_spinner)
                    builderAddContent.setView(dialogViewAddContent)
                    val modifyDialog :AlertDialog = builderAddContent.create()

                    dialogImgPlusBtn.visibility = View.GONE
                    dialogImageView.setImageURI(Uri.parse(board.image))
                    dialogAddBtn.text = "수정하기"
                    addName.text = board.name
                    addColor.text = board.color
                    addCapacity.text = board.capacity
                    addPrice.text = board.price
                    addExpiryDate.text = board.expiryDate
                    addEtc.text = board.etc

                    setStoreSpinner(storeSpinner)
                    setBrandSpinner(brandSpinner)
                    categorySpinnerLayout.visibility = View.GONE

                    modifyDialog.show()

                    dialogAddBtn.setOnClickListener {
                        var realm = MainActivity.realm
                        realm.beginTransaction()
                        val updateItem = realm.where<BoardInnerRealm>(BoardInnerRealm::class.java).equalTo("id",board.id).findFirst()!!
                        updateItem.name = addName.text.toString()
                        updateItem.color = addColor.text.toString()
                        updateItem.capacity= addCapacity.text.toString()
                        updateItem.price = addPrice.text.toString()
                        updateItem.expiryDate = addExpiryDate.text.toString()
                        updateItem.etc = addEtc.text.toString()
                        updateItem.brandName = brandSelected
                        updateItem.storeName = storeSelected
                        updateItem.category = getBoardName
                        realm.commitTransaction()
                        Toast.makeText(context,"변경되었습니다",Toast.LENGTH_SHORT).show()
                        modifyDialog.dismiss()
                    }

                    dialogCancelBtn.setOnClickListener {
                        modifyDialog.dismiss()
                    }
                }

                dBtn.setOnClickListener {
                    mordAlert.dismiss()
                    val builder = AlertDialog.Builder(context)
                    val dialogView = layoutInflaterInner.inflate(R.layout.dialog_delete_item, null)
                    val deleteBtn = dialogView.findViewById<Button>(R.id.dialog_delete_item_delete_btn)
                    val cancelBtn = dialogView.findViewById<Button>(R.id.dialog_delete_item_cancel_btn)
                    builder.setView(dialogView)
                    val deleteAlertDialog :AlertDialog = builder.create()
                    deleteAlertDialog.show()

                    deleteBtn.setOnClickListener {
                        realm.beginTransaction()
                        val deleteItem = realm.where<BoardInnerRealm>(BoardInnerRealm::class.java).equalTo("id", board.id).findFirst()!!
                        deleteItem.deleteFromRealm()
                        realm.commitTransaction()
                        Toast.makeText(context,"삭제되었습니다",Toast.LENGTH_SHORT).show()
                        deleteAlertDialog.dismiss()
                    }
                    cancelBtn.setOnClickListener {
                        deleteAlertDialog.dismiss()
                    }
                }

            }


            boardImage.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                val dialogView = layoutInflaterInner.inflate(R.layout.dialog_show_image, null)
                val dialogImageView = dialogView.findViewById<ImageView>(R.id.dialog_show_image_imgView)
                builder.setView(dialogView)
                val add :AlertDialog = builder.create()
                dialogImageView.setImageURI(Uri.parse(board.image))
                add.show()
            }
        }
    }

    inner class ViewHolderNoBrand(itemView: View):BaseViewHolder<BoardInner>(itemView){
        val boardImage = itemView.form_inner_board_image
        val boardName = itemView.form_inner_board_name
        val boardColor = itemView.form_inner_board_color
        val boardCapacity = itemView.form_inner_board_capacity
        val boardPrice = itemView.form_inner_board_price
        val boardExpiryDate = itemView.form_inner_board_expiryDate
        val boardBrand = itemView.form_inner_board_brandName
        val boardStore = itemView.form_inner_board_storeName
        val boardEtc = itemView.form_inner_board_etc
        val boardDeleteBtn = itemView.form_inner_board_delete_btn
        override fun bind(board: BoardInner) {
            if(board.image != "") {
                val uri = Uri.parse(board.image)
                boardImage.setImageURI(uri)
            }
            boardName?.text = "제품명 : " + board.name
            boardColor?.text = "컬러 : " + board.color
            boardCapacity?.text = "용량 : " + board.capacity
            boardPrice?.text = "가격 : " + board.price
            boardExpiryDate.text = "기한 : " + board.expiryDate
            boardBrand.text = "브랜드 : " + board.brandName
            boardStore.text = "스토어 : " + board.storeName
            boardEtc?.text = "메모 : " + board.etc

            var realm = MainActivity.realm

            boardDeleteBtn.setOnClickListener {
                val build = AlertDialog.Builder(context)
                val mOrDdialogView = layoutInflaterInner.inflate(R.layout.dialog_modify_or_delete, null)
                val mBtn = mOrDdialogView.findViewById<Button>(R.id.dialog_m_or_d_modify_btn)
                val dBtn = mOrDdialogView.findViewById<Button>(R.id.dialog_m_or_d_delete_btn)
                build.setView(mOrDdialogView)
                val mordAlert :AlertDialog = build.create()
                mordAlert.show()

                mBtn.setOnClickListener {
                    mordAlert.dismiss()

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
                    val brandSpinnerLayout = dialogViewAddContent.findViewById<LinearLayout>(R.id.dialog_item_brand_spinner_layout)
                    val storeSpinner = dialogViewAddContent.findViewById<Spinner>(R.id.dialog_item_store_spinner)
                    builderAddContent.setView(dialogViewAddContent)
                    val modifyDialog :AlertDialog = builderAddContent.create()

                    dialogImgPlusBtn.visibility = View.GONE
                    dialogImageView.setImageURI(Uri.parse(board.image))
                    dialogAddBtn.text = "수정하기"
                    addName.text = board.name
                    addColor.text = board.color
                    addCapacity.text = board.capacity
                    addPrice.text = board.price
                    addExpiryDate.text = board.expiryDate
                    addEtc.text = board.etc

                    setCategorySpinner(categorySpinner)
                    setStoreSpinner(storeSpinner)
                    brandSpinnerLayout.visibility = View.GONE

                    modifyDialog.show()

                    dialogAddBtn.setOnClickListener {
                        var realm = MainActivity.realm
                        realm.beginTransaction()
                        val updateItem = realm.where<BoardInnerRealm>(BoardInnerRealm::class.java).equalTo("id",board.id).findFirst()!!
                        updateItem.name = addName.text.toString()
                        updateItem.color = addColor.text.toString()
                        updateItem.capacity= addCapacity.text.toString()
                        updateItem.price = addPrice.text.toString()
                        updateItem.expiryDate = addExpiryDate.text.toString()
                        updateItem.etc = addEtc.text.toString()
                        updateItem.brandName = getBoardName
                        updateItem.storeName = storeSelected
                        updateItem.category = categorySelected
                        realm.commitTransaction()
                        Toast.makeText(context,"변경되었습니다",Toast.LENGTH_SHORT).show()
                        modifyDialog.dismiss()
                    }

                    dialogCancelBtn.setOnClickListener {
                        modifyDialog.dismiss()
                    }
                }

                dBtn.setOnClickListener {
                    mordAlert.dismiss()
                    val builder = AlertDialog.Builder(context)
                    val dialogView = layoutInflaterInner.inflate(R.layout.dialog_delete_item, null)
                    val deleteBtn = dialogView.findViewById<Button>(R.id.dialog_delete_item_delete_btn)
                    val cancelBtn = dialogView.findViewById<Button>(R.id.dialog_delete_item_cancel_btn)
                    builder.setView(dialogView)
                    val deleteAlertDialog :AlertDialog = builder.create()
                    deleteAlertDialog.show()

                    deleteBtn.setOnClickListener {
                        realm.beginTransaction()
                        val deleteItem = realm.where<BoardInnerRealm>(BoardInnerRealm::class.java).equalTo("id", board.id).findFirst()!!
                        deleteItem.deleteFromRealm()
                        realm.commitTransaction()
                        Toast.makeText(context,"삭제되었습니다",Toast.LENGTH_SHORT).show()
                        deleteAlertDialog.dismiss()
                    }
                    cancelBtn.setOnClickListener {
                        deleteAlertDialog.dismiss()
                    }
                }

            }


            boardImage.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                val dialogView = layoutInflaterInner.inflate(R.layout.dialog_show_image, null)
                val dialogImageView = dialogView.findViewById<ImageView>(R.id.dialog_show_image_imgView)
                builder.setView(dialogView)
                val add :AlertDialog = builder.create()
                dialogImageView.setImageURI(Uri.parse(board.image))
                add.show()
            }
        }
    }

    inner class ViewHolderNoStore(itemView: View):BaseViewHolder<BoardInner>(itemView){
        val boardImage = itemView.form_inner_board_image
        val boardName = itemView.form_inner_board_name
        val boardColor = itemView.form_inner_board_color
        val boardCapacity = itemView.form_inner_board_capacity
        val boardPrice = itemView.form_inner_board_price
        val boardExpiryDate = itemView.form_inner_board_expiryDate
        val boardBrand = itemView.form_inner_board_brandName
        val boardStore = itemView.form_inner_board_storeName
        val boardEtc = itemView.form_inner_board_etc
        val boardDeleteBtn = itemView.form_inner_board_delete_btn
        override fun bind(board: BoardInner) {
            if(board.image != "") {
                val uri = Uri.parse(board.image)
                boardImage.setImageURI(uri)
            }
            boardName?.text = "제품명 : " + board.name
            boardColor?.text = "컬러 : " + board.color
            boardCapacity?.text = "용량 : " + board.capacity
            boardPrice?.text = "가격 : " + board.price
            boardExpiryDate.text = "기한 : " + board.expiryDate
            boardBrand.text = "브랜드 : " + board.brandName
            boardStore.text = "스토어 : " + board.storeName
            boardEtc?.text = "메모 : " + board.etc

            var realm = MainActivity.realm

            boardDeleteBtn.setOnClickListener {
                val build = AlertDialog.Builder(context)
                val mOrDdialogView = layoutInflaterInner.inflate(R.layout.dialog_modify_or_delete, null)
                val mBtn = mOrDdialogView.findViewById<Button>(R.id.dialog_m_or_d_modify_btn)
                val dBtn = mOrDdialogView.findViewById<Button>(R.id.dialog_m_or_d_delete_btn)
                build.setView(mOrDdialogView)
                val mordAlert :AlertDialog = build.create()
                mordAlert.show()

                mBtn.setOnClickListener {
                    mordAlert.dismiss()

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
                    val brandSpinner = dialogViewAddContent.findViewById<Spinner>(R.id.dialog_item_brand_spinner)
                    val storeSpinnerLayout = dialogViewAddContent.findViewById<LinearLayout>(R.id.dialog_item_store_spinner_layout)
                    builderAddContent.setView(dialogViewAddContent)
                    val modifyDialog :AlertDialog = builderAddContent.create()

                    dialogImgPlusBtn.visibility = View.GONE
                    dialogImageView.setImageURI(Uri.parse(board.image))
                    dialogAddBtn.text = "수정하기"
                    addName.text = board.name
                    addColor.text = board.color
                    addCapacity.text = board.capacity
                    addPrice.text = board.price
                    addExpiryDate.text = board.expiryDate
                    addEtc.text = board.etc

                    setCategorySpinner(categorySpinner)
                    setBrandSpinner(brandSpinner)
                    storeSpinnerLayout.visibility = View.GONE

                    modifyDialog.show()

                    dialogAddBtn.setOnClickListener {
                        var realm = MainActivity.realm
                        realm.beginTransaction()
                        val updateItem = realm.where<BoardInnerRealm>(BoardInnerRealm::class.java).equalTo("id",board.id).findFirst()!!
                        updateItem.name = addName.text.toString()
                        updateItem.color = addColor.text.toString()
                        updateItem.capacity= addCapacity.text.toString()
                        updateItem.price = addPrice.text.toString()
                        updateItem.expiryDate = addExpiryDate.text.toString()
                        updateItem.etc = addEtc.text.toString()
                        updateItem.brandName = brandSelected
                        updateItem.storeName = getBoardName
                        updateItem.category = categorySelected
                        realm.commitTransaction()
                        Toast.makeText(context,"변경되었습니다",Toast.LENGTH_SHORT).show()
                        modifyDialog.dismiss()
                    }

                    dialogCancelBtn.setOnClickListener {
                        modifyDialog.dismiss()
                    }
                }

                dBtn.setOnClickListener {
                    mordAlert.dismiss()
                    val builder = AlertDialog.Builder(context)
                    val dialogView = layoutInflaterInner.inflate(R.layout.dialog_delete_item, null)
                    val deleteBtn = dialogView.findViewById<Button>(R.id.dialog_delete_item_delete_btn)
                    val cancelBtn = dialogView.findViewById<Button>(R.id.dialog_delete_item_cancel_btn)
                    builder.setView(dialogView)
                    val deleteAlertDialog :AlertDialog = builder.create()
                    deleteAlertDialog.show()

                    deleteBtn.setOnClickListener {
                        realm.beginTransaction()
                        val deleteItem = realm.where<BoardInnerRealm>(BoardInnerRealm::class.java).equalTo("id", board.id).findFirst()!!
                        deleteItem.deleteFromRealm()
                        realm.commitTransaction()
                        Toast.makeText(context,"삭제되었습니다",Toast.LENGTH_SHORT).show()
                        deleteAlertDialog.dismiss()
                    }
                    cancelBtn.setOnClickListener {
                        deleteAlertDialog.dismiss()
                    }
                }

            }


            boardImage.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                val dialogView = layoutInflaterInner.inflate(R.layout.dialog_show_image, null)
                val dialogImageView = dialogView.findViewById<ImageView>(R.id.dialog_show_image_imgView)
                builder.setView(dialogView)
                val add :AlertDialog = builder.create()
                dialogImageView.setImageURI(Uri.parse(board.image))
                add.show()
            }
        }
    }


    inner class ViewHolderAll(itemView: View):BaseViewHolder<BoardInner>(itemView){
        val boardImage = itemView.form_inner_board_image
        val boardName = itemView.form_inner_board_name
        val boardColor = itemView.form_inner_board_color
        val boardCapacity = itemView.form_inner_board_capacity
        val boardPrice = itemView.form_inner_board_price
        val boardExpiryDate = itemView.form_inner_board_expiryDate
        val boardBrand = itemView.form_inner_board_brandName
        val boardStore = itemView.form_inner_board_storeName
        val boardEtc = itemView.form_inner_board_etc
        val boardDeleteBtn = itemView.form_inner_board_delete_btn
        override fun bind(board: BoardInner) {
            if(board.image != "") {
                val uri = Uri.parse(board.image)
                boardImage.setImageURI(uri)
            }
            boardName?.text = "제품명 : " + board.name
            boardColor?.text = "컬러 : " + board.color
            boardCapacity?.text = "용량 : " + board.capacity
            boardPrice?.text = "가격 : " + board.price
            boardExpiryDate.text = "기한 : " + board.expiryDate
            boardBrand.text = "브랜드 : " + board.brandName
            boardStore.text = "스토어 : " + board.storeName
            boardEtc?.text = "메모 : " + board.etc

            var realm = MainActivity.realm

            boardDeleteBtn.setOnClickListener {
                val build = AlertDialog.Builder(context)
                val mOrDdialogView = layoutInflaterInner.inflate(R.layout.dialog_modify_or_delete, null)
                val mBtn = mOrDdialogView.findViewById<Button>(R.id.dialog_m_or_d_modify_btn)
                val dBtn = mOrDdialogView.findViewById<Button>(R.id.dialog_m_or_d_delete_btn)
                build.setView(mOrDdialogView)
                val mordAlert :AlertDialog = build.create()
                mordAlert.show()

                mBtn.setOnClickListener {
                    mordAlert.dismiss()

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
                    val brandSpinner = dialogViewAddContent.findViewById<Spinner>(R.id.dialog_item_brand_spinner)
                    val storeSpinner = dialogViewAddContent.findViewById<Spinner>(R.id.dialog_item_store_spinner)
                    builderAddContent.setView(dialogViewAddContent)
                    val modifyDialog :AlertDialog = builderAddContent.create()

                    dialogImgPlusBtn.visibility = View.GONE
                    dialogImageView.setImageURI(Uri.parse(board.image))
                    dialogAddBtn.text = "수정하기"
                    addName.text = board.name
                    addColor.text = board.color
                    addCapacity.text = board.capacity
                    addPrice.text = board.price
                    addExpiryDate.text = board.expiryDate
                    addEtc.text = board.etc

                    setCategorySpinner(categorySpinner)
                    setStoreSpinner(storeSpinner)
                    setBrandSpinner(brandSpinner)

                    modifyDialog.show()

                    dialogAddBtn.setOnClickListener {
                        var realm = MainActivity.realm
                        realm.beginTransaction()
                        val updateItem = realm.where<BoardInnerRealm>(BoardInnerRealm::class.java).equalTo("id",board.id).findFirst()!!
                        updateItem.name = addName.text.toString()
                        updateItem.color = addColor.text.toString()
                        updateItem.capacity= addCapacity.text.toString()
                        updateItem.price = addPrice.text.toString()
                        updateItem.expiryDate = addExpiryDate.text.toString()
                        updateItem.etc = addEtc.text.toString()
                        updateItem.brandName = brandSelected
                        updateItem.storeName = storeSelected
                        updateItem.category = categorySelected
                        realm.commitTransaction()
                        Toast.makeText(context,"변경되었습니다",Toast.LENGTH_SHORT).show()
                        modifyDialog.dismiss()
                    }

                    dialogCancelBtn.setOnClickListener {
                        modifyDialog.dismiss()
                    }
                }

                dBtn.setOnClickListener {
                    mordAlert.dismiss()
                    val builder = AlertDialog.Builder(context)
                    val dialogView = layoutInflaterInner.inflate(R.layout.dialog_delete_item, null)
                    val deleteBtn = dialogView.findViewById<Button>(R.id.dialog_delete_item_delete_btn)
                    val cancelBtn = dialogView.findViewById<Button>(R.id.dialog_delete_item_cancel_btn)
                    builder.setView(dialogView)
                    val deleteAlertDialog :AlertDialog = builder.create()
                    deleteAlertDialog.show()

                    deleteBtn.setOnClickListener {
                        realm.beginTransaction()
                        val deleteItem = realm.where<BoardInnerRealm>(BoardInnerRealm::class.java).equalTo("id", board.id).findFirst()!!
                        deleteItem.deleteFromRealm()
                        realm.commitTransaction()
                        Toast.makeText(context,"삭제되었습니다",Toast.LENGTH_SHORT).show()
                        deleteAlertDialog.dismiss()
                    }
                    cancelBtn.setOnClickListener {
                        deleteAlertDialog.dismiss()
                    }
                }

            }


            boardImage.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                val dialogView = layoutInflaterInner.inflate(R.layout.dialog_show_image, null)
                val dialogImageView = dialogView.findViewById<ImageView>(R.id.dialog_show_image_imgView)
                builder.setView(dialogView)
                val add :AlertDialog = builder.create()
                dialogImageView.setImageURI(Uri.parse(board.image))
                add.show()
            }
        }
    }
    inner class ViewHolder2(itemView: View):BaseViewHolder<BoardInner>(itemView){
        val boardPlusBtn = itemView.form_plus_btn
        override fun bind(item: BoardInner) {
            boardPlusBtn.setOnClickListener {
                itemClick(item)
            }
        }
    }


    val TYPE_NO_CATEGORY_SPINNER = 100
    val TYPE_NO_BRAND_SPINNER = 101
    val TYPE_NO_STORE_SPINNER = 102

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when(viewType){
            TYPE_NO_CATEGORY_SPINNER -> {
                val view = LayoutInflater.from(context).inflate(R.layout.form_inner_item_board, parent, false)
                ViewHolderNoCategory(view)
            }
            TYPE_NO_BRAND_SPINNER -> {
                val view = LayoutInflater.from(context).inflate(R.layout.form_inner_item_board, parent, false)
                ViewHolderNoBrand(view)
            }
            TYPE_NO_STORE_SPINNER -> {
                val view = LayoutInflater.from(context).inflate(R.layout.form_inner_item_board, parent, false)
                ViewHolderNoStore(view)
            }
            0 -> {
                val view = LayoutInflater.from(context).inflate(R.layout.form_inner_item_board, parent, false)
                ViewHolderAll(view)
            }
            1 -> {
                val view = LayoutInflater.from(context).inflate(R.layout.form_inner_plus_btn, parent, false)
                ViewHolder2(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = boardList[position]
        when(holder){
            is ViewHolderNoCategory -> holder.bind(element)
            is ViewHolderNoBrand -> holder.bind(element)
            is ViewHolderNoStore -> holder.bind(element)
            is ViewHolderAll -> holder.bind(element)
            is ViewHolder2 -> holder.bind(element)
            else -> throw IllegalArgumentException()
        }
    }


    override fun getItemViewType(position: Int): Int {
        return boardList[position].type!!
    }

    override fun getItemCount(): Int {
        return boardList.size
    }
}