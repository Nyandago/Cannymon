package com.cannybits.cannymon

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.cannybits.cannymon.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import java.lang.Exception

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    var ACCESS_LOCATION = 123
    var location: Location? = null
    var listPokemon = ArrayList<Pokemon>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap



        checkPermission()
        loadPokemon()
    }



    fun checkPermission(){
        if(Build.VERSION.SDK_INT >= 23){
            if (ActivityCompat.checkSelfPermission(this,
                  android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),ACCESS_LOCATION)
                    return
                    }
            }

        getUserLocation()
    }

    fun getUserLocation(){
        Toast.makeText(this,"My Current Location On",Toast.LENGTH_LONG).show()

        var myLocation = MyLocationListener()
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3,3f,myLocation)

        var myThread = myThread()
        myThread.start()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        when(requestCode){
            ACCESS_LOCATION ->{
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    getUserLocation()
                } else{
                    Toast.makeText(this,"Location Access Denied",Toast.LENGTH_LONG).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    inner class MyLocationListener : LocationListener{

        constructor(){
            location = Location("Start")
            location!!.longitude = 0.0
            location!!.latitude = 0.0
        }

        override fun onLocationChanged(p0: Location) {
          location = p0
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
           // super.onStatusChanged(provider, status, extras)
        }

        override fun onProviderEnabled(provider: String) {
          //  super.onProviderEnabled(provider)
        }

        override fun onProviderDisabled(provider: String) {
          //  super.onProviderDisabled(provider)
        }
    }

    inner class myThread : Thread {
        constructor():super(){

        }

        override fun run(){
            while(true){

                try {
                    runOnUiThread(){


                        mMap!!.clear()
                        //show me
                    val dsm = LatLng(location!!.longitude, location!!.latitude)
                    mMap!!.addMarker(MarkerOptions()
                        .position(dsm)
                        .title("Canny Bits")
                        .snippet("my current location")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.mario)))
                    mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(dsm,2f))

                       // show other pokemons
                        for (i in 0..listPokemon.size-1){
                            var newPokemon = listPokemon[i]

                            if (newPokemon.isCatch == false) {
                                val newPokemonLoc = LatLng(newPokemon.lat!!, newPokemon.lon!!)
                                mMap!!.addMarker(
                                    MarkerOptions()
                                        .position(newPokemonLoc)
                                        .title(newPokemon.name)
                                        .snippet(newPokemon.myDescription)
                                        .icon(BitmapDescriptorFactory.fromResource(newPokemon.image!!))
                                )
                            }

                        }
                }
                    Thread.sleep(1000)
                } catch (ex: Exception){

                }
            }
        }
    }

    fun loadPokemon(){
        listPokemon.add(
            Pokemon("Charmander",
            R.drawable.charmander, "This is Chalamander from Mtwara", -10.27792, 40.18863)
        )
        listPokemon.add(Pokemon("Bulbasaur",
            R.drawable.bulbasaur, "This is Bulbasaur from Cassablanca", 33.57869, -7.67))
        listPokemon.add(Pokemon("Squirtle",
            R.drawable.squirtle , "This is Squirtle from Lubumbashi", -11.684049728038131, 27.485699924360183))
    }
}