package com.domain.weatherapp.core.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.domain.weatherapp.core.di.GPS_REQUEST
import com.domain.weatherapp.core.di.LOCATION_REQUEST
import com.domain.weatherapp.core.di.turnOnGps
import com.google.android.gms.location.*

abstract class GpsActivity : BaseActivity() {

    var fusedLocationClient: FusedLocationProviderClient? = null


    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            removeLocationUpdates()
            if (locationResult == null) {
                failedToCaptureLocation()
            } else {
                val location = locationResult.locations.maxBy { it.accuracy }
                onLocationCaptured(location);
            }


        }
    }

    abstract fun onLocationCaptured(location: Location?);
    abstract fun failedToCaptureLocation();
    abstract fun permissionAccessLocationDenied();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    override fun onResume() {
        super.onResume()

    }


    private fun removeLocationUpdates() {
        fusedLocationClient?.removeLocationUpdates(locationCallback)
    }

    fun startLocationUpdates() {
        fusedLocationClient?.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null
        )
    }

    fun invokeLocationAction() {
        when {
            !isGpsEnabled() -> {

                turnOnGps()
            }
            isPermissionsGranted() -> {

                startLocationUpdates()
            }

            shouldShowRequestPermissionRationale() -> {

                permissionAccessLocationDenied()
            }
            else -> {
                requestPermission()
            }
        }
    }


    fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            LOCATION_REQUEST
        )
    }


    private fun shouldShowRequestPermissionRationale() =
        ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) && ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )


    private fun isPermissionsGranted() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == GPS_REQUEST && resultCode == Activity.RESULT_OK) {
            invokeLocationAction()
        } else {
            failedToCaptureLocation()
        }
        super.onActivityResult(requestCode, resultCode, data)


    }


    companion object {
        val locationRequest: LocationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_REQUEST -> {
                invokeLocationAction()
            }
        }
    }

    fun isGpsEnabled(): Boolean {
        val locationManager =
            getSystemService(Context.LOCATION_SERVICE) as android.location.LocationManager

        return locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)

    }


}