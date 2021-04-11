package org.beautyinside.Board

import android.graphics.Bitmap

class BoardInner(var id:Long,
                var image:String,
                 var name:String? = null,
                 var color:String? = null,
                 var capacity:String? = null,
                 var price:String? = null,
                 var expiryDate:String? = null,
                 var etc:String? = null,
                 var category:String? = null,
                 var brandName:String?=null,
                 var storeName:String?=null,
                 var type:Int? = null) {
}