package com.domain.weatherapp.ui.activities

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.domain.weatherapp.R
import com.domain.weatherapp.core.data.remote.Result
import com.domain.weatherapp.core.ui.GpsActivity
import com.domain.weatherapp.ui.fragments.WeatherListFragmentDirections
import com.domain.weatherapp.weather.data.model.WeatherModel
import com.domain.weatherapp.ui.viewmodel.WeatherViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class WeatherActivity : GpsActivity() {

    @Inject
    lateinit var viewModel: WeatherViewModel
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    lateinit var snackbar: Snackbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        snackbar=Snackbar.make(root, "", Snackbar.LENGTH_LONG)
        navController = findNavController(R.id.nav_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph, null)

        viewModel = injectViewModel();
        viewModel.weatherByCoord.observe(this, Observer {
            onResult(it)
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.clear()
        getMenuInflater().inflate(R.menu.menu_navigation, menu);
        return super.onCreateOptionsMenu(menu)
    }

    private var menuItem: MenuItem? = null;


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        menuItem = item;
        when (item.itemId) {
            R.id.locateMe -> {
                showLoading()
                invokeLocationAction()


            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showMessage(message: String) {
       snackbar.setText(message).show()
    }

    private fun subscribe(lat: String, lon: String) {
        viewModel.subscribeToWeather(lat, lon)
    }

    private fun onResult(it: Result<WeatherModel>) {
        when (it.status) {
            Result.Status.LOADING -> {
                showLoading()
            }
            Result.Status.SUCCESS -> {
                stopLoading()
                it.data?.let {
                    if (navController.currentDestination?.id != R.id.forecast_fragment) {
                        val direction =
                            WeatherListFragmentDirections.actionListToForecast(
                                it
                            )
                        navController.navigate(direction)
                    }

                }

            }
            Result.Status.ERROR -> {
                stopLoading()
                it.message?.let {

                    showMessage(it)
                }


            }
        }
    }

    private fun stopLoading() {

        getAnimation()?.cancel()
    }

    private fun showLoading() {
        menuItem?.let {
            val imgView = layoutInflater.inflate(R.layout.image_view, null, true)
            it.setActionView(imgView)
            it.actionView.startAnimation(getAnimation())
            imgView.setOnClickListener {
                showLoading()
                invokeLocationAction()
            }
        }

    }

    private var animation: Animation? = null;

    fun getAnimation(): Animation? {
        if (animation == null) {
            val mAnimation: Animation = AlphaAnimation(1.0.toFloat(), 0.toFloat())
            mAnimation.duration = 200
            mAnimation.interpolator = LinearInterpolator()
            mAnimation.repeatCount = Animation.INFINITE
            mAnimation.repeatMode = Animation.REVERSE
            animation = mAnimation
        }

        return animation;
    }

    override fun onLocationCaptured(location: Location?) = if (location == null) {
        showMessage(getString(R.string.unable_to_access_location))
    } else {

        subscribe(
            location.latitude.toString(), location.longitude.toString()
        )

    }

    override fun permissionAccessLocationDenied() {
        stopLoading()
        showMessage("Permission denied")
    }

    override fun failedToCaptureLocation() {
        stopLoading()
        showMessage("Unable to capture location")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

}


