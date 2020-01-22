package com.domain.weatherapp

import android.app.Activity.RESULT_CANCELED
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.domain.weatherapp.ui.activities.WeatherActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherActivityTest {

    @get:Rule
    val intentsTestRule = IntentsTestRule(WeatherActivity::class.java)

    @Test
    fun  test_cameraIntent() {
        val result = ActivityResult(RESULT_CANCELED, Intent());
        intending(toPackage(WeatherActivity::class.java.name)).respondWith(result);
        val activity=intentsTestRule.activity;
        activity.startActivityForResult( Intent(activity,
            WeatherActivity::class.java),100);
        activity.snackbar.isShown
        //onView(withId(com.google.android.material.R.id.snackbar_text)).check(matches(withText(R.string.unable_to_access_location)))
        //onView(withId(R.id.txt_test)).check(matches(isDisplayed()))

    }


}
