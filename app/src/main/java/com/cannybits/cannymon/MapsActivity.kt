package com.cannybits.cannymon

import android.content.pm.PackageManager
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

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    var ACCESS_LOCATION = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker Dar Es Salaam, Tanzania.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Dar Es Salaam and move the camera
        val dsm = LatLng(-6.7924, 39.2083)
        mMap!!.addMarker(MarkerOptions()
            .position(dsm)
            .title("Canny Bits")
            .snippet("my current location")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.mario))
        )
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(dsm,10f))

        checkPermission()
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
}