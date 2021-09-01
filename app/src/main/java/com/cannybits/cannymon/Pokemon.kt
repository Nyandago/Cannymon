package com.cannybits.cannymon

import android.location.Location

class Pokemon() {
    var name: String? = null
    var image: Int? = null
    var myDescription : String? = null
    var location: Location? = null
    var isCatch: Boolean? = false
    var power: Double? = null

   constructor(name: String, image: Int,power: Double, myDesc: String, lat: Double, lon: Double) : this() {
       this.name = name
       this.image = image
       this.myDescription= myDesc
       this.location = Location(name)
       this.location!!.longitude = lon
       this.location!!.latitude = lat
       this.power = power
       this.isCatch = false
   }

}