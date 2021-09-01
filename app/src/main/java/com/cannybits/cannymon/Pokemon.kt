package com.cannybits.cannymon

class Pokemon() {
    var name: String? = null
    var image: Int? = null
    var myDescription : String? = null
    var lat: Double? = null
    var lon: Double? = null
    var isCatch: Boolean? = false
    var power: Double? = null

   constructor(name: String, image: Int,power: Double, myDesc: String, lat: Double, lon: Double) : this() {
       this.name = name
       this.image = image
       this.myDescription= myDesc
       this.lat = lat
       this.lon = lon
       this.power = power
       this.isCatch = false
   }

}