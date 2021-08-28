package com.cannybits.cannymon

class Pokemon(name: String, image: Int, myDesc: String, lat: Double, lon: Double) {
    var name: String? = name
    var image: Int? = image
    var myDescription : String? = myDesc
    var lat: Double? = lat
    var lon: Double? = lon
    var isCatch: Boolean? = false

    init {
        this.isCatch = false
    }

}