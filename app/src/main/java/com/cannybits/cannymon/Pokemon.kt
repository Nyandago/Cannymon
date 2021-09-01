package com.cannybits.cannymon

class Pokemon(name: String, image: Int,power: Double, myDesc: String, lat: Double, lon: Double) {
    var name: String? = name
    var image: Int? = image
    var myDescription : String? = myDesc
    var lat: Double? = lat
    var lon: Double? = lon
    var isCatch: Boolean? = false
    var power: Double? = 0.0

    init {
        this.isCatch = false
       }

}