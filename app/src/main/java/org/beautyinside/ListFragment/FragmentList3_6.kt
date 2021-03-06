package org.beautyinside.ListFragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.RealmResults
import io.realm.kotlin.createObject
import kotlinx.android.synthetic.main.activity_fragment_home.*
import kotlinx.android.synthetic.main.category2.*
import org.beautyinside.Adapter.RecyclerAdapterItemList
import org.beautyinside.Adapter.RecyclerAdapterItemList_Inner
import org.beautyinside.Board.Board
import org.beautyinside.Board.BoardInner
import org.beautyinside.Board.BoardInnerRealm
import org.beautyinside.Board.BrandRealm
import org.beautyinside.MainActivity
import org.beautyinside.R

class FragmentList3_6 : Fragment() {
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



    lateinit var userAddImg : ImageView
    lateinit var imgPlusBtn : Button
    lateinit var firstAll : RealmResults<BoardInnerRealm>
    var firstList :MutableList<BoardInner> = mutableListOf()
    lateinit var storeItemListAdapter :RecyclerAdapterItemList_Inner
    val REQUEST_GET_IMAGE = 105
    var brandSelected = "??????"
    var categorySelected = "??????"
    var brandItems : MutableList<String> = mutableListOf("??????")
    var finalBoardInner:BoardInner = BoardInner(0, "","","","","","","","","","",102)

    override fun onResume() {
        super.onResume()
        firstList = mutableListOf()

        firstAll = MainActivity.realm.where(BoardInnerRealm::class.java).equalTo("storeName","?????????").findAll()
        for(element in firstAll){
            firstList.add(BoardInner(element.id!!, element.image!!, element.name, element.color, element.capacity, element.price, element.expiryDate, element.etc, element.category, element.brandName, element.storeName, 102))
        }
        firstList.add(BoardInner(0, "","","","","","","","","","",1))

        storeItemListAdapter = RecyclerAdapterItemList_Inner(context!!, firstList, layoutInflater,"?????????"){
            val builderAddContent = AlertDialog.Builder(context)
            val dialogViewAddContent = layoutInflater.inflate(R.layout.dialog_add_item, null)
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
            builderAddContent.setView(dialogViewAddContent)
            val add : AlertDialog = builderAddContent.create()
            add.show()
            val storeSpinnerLayout= dialogViewAddContent.findViewById<LinearLayout>(R.id.dialog_item_store_spinner_layout)
            storeSpinnerLayout.visibility = View.GONE
            setCategorySpinner(categorySpinner)
            setBrandSpinner(brandSpinner)

            //????????? ???????????? ??????
            dialogImgPlusBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                userAddImg = dialogImageView!!
                imgPlusBtn = dialogImgPlusBtn!!
                intent.type = "image/*"
                startActivityForResult(intent, REQUEST_GET_IMAGE)
            }
            //????????? ?????? ???????????? ???
            dialogImageView.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK)
                userAddImg = dialogImageView!!
                imgPlusBtn = dialogImgPlusBtn!!
                intent.type = "image/*"
                startActivityForResult(intent, REQUEST_GET_IMAGE)
            }

            dialogAddBtn.setOnClickListener {
                if(dialogImgPlusBtn.visibility != View.VISIBLE) {
                    finalBoardInner.name = addName.text.toString()
                    finalBoardInner.color = addColor.text.toString()
                    finalBoardInner.capacity = addCapacity.text.toString()
                    finalBoardInner.price = addPrice.text.toString()
                    finalBoardInner.expiryDate = addExpiryDate.text.toString()
                    finalBoardInner.etc= addEtc.text.toString()
                    finalBoardInner.brandName = brandSelected
                    finalBoardInner.storeName = "?????????"
                    finalBoardInner.category = categorySelected

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
                    add.dismiss()
                }else{
                    Toast.makeText(context,"????????? ??????????????????",Toast.LENGTH_SHORT).show()
                }
            }

            dialogCancelBtn.setOnClickListener {
                finalBoardInner = BoardInner(0, "","","","","","","","","","",102)
                add.dismiss()
            }
        }
        category2_recyclerView.adapter = storeItemListAdapter
        category2_recyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



    }

    private fun setCategorySpinner(spinner:Spinner){
        val categoryItems :MutableList<String> = mutableListOf(
            "??????/??????","??????/?????????","?????????/??????","??????","?????????","???????????????","???????????????","??????????????????",
            "?????????","?????????/????????????","BB??????","CC??????","??????????????????","?????????","??????/??????","?????????","????????????","????????????",
            "?????????","???????????????","???????????????","???????????????","????????????","??????????????????","?????????","??????","???????????????","?????????????????????",
            "???????????????","?????????","?????????/??????","??????????????????","??????????????????","?????????????????????","??????????????????","????????????","???????????????",
            "?????????","?????????","?????????","??????","???????????????","????????????/??????","????????????/???","???????????????","???????????????","???????????????","???????????????",
            "??????","??????/????????????","???????????????","????????????","?????????","?????????/?????????","???????????????","???????????????","????????????","???????????????","????????????",
            "????????????","????????????","????????????","????????????","????????????","??????","?????????","??????????????????","??????????????????","????????????","????????????","????????????"
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
    private fun setBrandSpinner(spinner:Spinner){
        val brandAll = MainActivity.realm.where(BrandRealm::class.java).findAll()
        brandItems = mutableListOf("??????")
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

}
