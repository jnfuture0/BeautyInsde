package org.beautyinside.Board

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class BrandRealm (
    @PrimaryKey
    var id:Long?=0,
    var brandName :String? = null
):RealmObject()
