package com.domain.weatherapp.ui

import android.view.View
import androidx.lifecycle.MediatorLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.domain.weatherapp.R
import com.domain.weatherapp.SingleFragmentActivity
import com.domain.weatherapp.core.data.remote.Result
import com.domain.weatherapp.util.DataBindingIdlingResourceRule
import com.domain.weatherapp.util.EspressoTestUtil
import com.domain.weatherapp.util.ViewModelUtil
import com.domain.weatherapp.weather.data.model.WeatherModel
import com.domain.weatherapp.ui.fragments.WeatherListFragment
import com.domain.weatherapp.ui.viewmodel.WeatherViewModel
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
@RunWith(AndroidJUnit4::class)
class TestWeatherList {

    @get:Rule val activityRule = ActivityTestRule(SingleFragmentActivity::class.java, true, true)

    @get:Rule val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule(activityRule)
    private lateinit var viewModel: WeatherViewModel
    private val weatherModel = MediatorLiveData<Result<List<WeatherModel>>>()
    private val testFragment =
        WeatherListFragment()
    private val navController = com.domain.weather.util.mock<NavController>()

    @Before
    fun setup() {
        viewModel = mock(WeatherViewModel::class.java)
        `when`(viewModel.weatherByCity).thenReturn(weatherModel)
        //doNothing().`when`(viewModel).setLogin(anyString())

        testFragment.viewModelProvider = ViewModelUtil.createFor(viewModel)

        Navigation.setViewNavController(
            activityRule.activity.findViewById<View>(R.id.container),
            navController
        )
        activityRule.activity.setFragment(testFragment)

        EspressoTestUtil.disableProgressBarAnimations(activityRule)

    }

    @Test
    fun viewVisibility_changingViewModelValue_DisplayLoadingAndHideMessage() {
        weatherModel.postValue(Result.loading())
        onView(ViewMatchers.withId(R.id.progress_bar)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.text_message)).check(matches(not(ViewMatchers.isDisplayed())))
    }
    @Test
    fun viewVisibility_changingViewModelValue_error() {
        weatherModel.postValue(Result.loading())
        weatherModel.postValue(Result.error("error", null))
        onView(ViewMatchers.withId(R.id.progress_bar)).check(matches(not(ViewMatchers.isDisplayed())))
    }



}