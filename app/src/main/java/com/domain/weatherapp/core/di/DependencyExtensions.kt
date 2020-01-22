package com.domain.weatherapp.core.di

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.IntentSender
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*

const val GPS_REQUEST = 100
const val LOCATION_REQUEST = 101

/**
 * Kotlin extensions for dependency injection
 */

fun FragmentActivity.turnOnGps() {
    val locationRequest: LocationRequest = LocationRequest.create().apply {
        interval = 10000
        fastestInterval = 5000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }
    val settingsClient: SettingsClient = LocationServices.getSettingsClient(this)
    val locationSettingsRequest: LocationSettingsRequest?
    val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val builder = LocationSettingsRequest.Builder()
        .addLocationRequest(locationRequest)
    locationSettingsRequest = builder.build()
    builder.setAlwaysShow(true)

    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        settingsClient
            .checkLocationSettings(locationSettingsRequest)
            .addOnSuccessListener(this) {
                //  GPS is already enable, callback GPS status through listener
            }
            .addOnFailureListener(this) { e ->
                when ((e as ApiException).statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the
                            // result in onActivityResult().
                            val rae = e as ResolvableApiException
                            rae.startResolutionForResult(this, GPS_REQUEST)
                        } catch (sie: IntentSender.SendIntentException) {
                            Log.i(ContentValues.TAG, "PendingIntent unable to execute request.")
                        }

                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        val errorMessage =
                            "Location settings are inadequate, and cannot be " + "fixed here. Fix in Settings."
                        Log.e(ContentValues.TAG, errorMessage)

                        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                    }
                }
            }
    }

}


