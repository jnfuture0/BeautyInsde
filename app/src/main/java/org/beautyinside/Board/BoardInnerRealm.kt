package org.beautyinside.Board

import android.graphics.Bitmap
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class BoardInnerRealm(
    @PrimaryKey
    var id:Long? = 0,
    var category:String? = null,
    var image:String? = null,
    var name:String? = null,
    var color:String? = null,
    var capacity:String? = null,
    var price:String? = null,
    var expiryDate:String? = null,
    var etc:String? = null,
    var brandName:String?=null,
    var storeName:String?=null,
    var type:Int? = null):RealmObject() {
}