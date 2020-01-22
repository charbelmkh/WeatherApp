package com.domain.weatherapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.domain.weatherapp.weather.data.model.WeatherList
import com.domain.weatherapp.weather.domain.impl.WeatherUseCaseImpl
import com.domain.weatherapp.ui.viewmodel.WeatherViewModel
import com.google.gson.Gson
import org.apache.commons.io.IOUtils
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*
import java.nio.charset.StandardCharsets

@RunWith(JUnit4::class)
class WeatherViewModelTest {


    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()


    private val useCase = mock(WeatherUseCaseImpl::class.java)
    private var viewModel =
        WeatherViewModel(useCase)
    val gson = Gson()

    //[UnitOfWork_StateUnderTest_ExpectedBehavior]
    @Test
    fun transform_hasData_returnGroupedObjectWithSizeOne() {
        var forecastItem = parseData("forecast_same_date.json")
        val result = com.domain.weatherapp.core.data.remote.Result.success(forecastItem)
        val newForm = viewModel.transform(result)
        Assert.assertTrue(newForm.data?.size==1)

    }

    @Test
    fun transform_hasData_returnGroupedObjectWithSizeTwo() {
        var forecastItem = parseData("forecast_different_date.json")
        val result = com.domain.weatherapp.core.data.remote.Result.success(forecastItem)
        val newForm = viewModel.transform(result)
        Assert.assertTrue(newForm.data?.size==2)

    }
    private fun parseData(fileName: String): WeatherList {
        val inputStream = javaClass.classLoader?.getResourceAsStream("api-response/$fileName")
        val source = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        return gson.fromJson(source, WeatherList::class.java)

    }

}