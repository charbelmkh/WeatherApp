package com.domain.weatherapp.data.remote.services

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.domain.weatherapp.data.Contants.Companion.CITY_NAME
import com.domain.weatherapp.data.Contants.Companion.LATITUDE
import com.domain.weatherapp.data.Contants.Companion.LONGITUDE
import com.domain.weatherapp.weather.data.remote.services.WeatherService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.apache.commons.io.IOUtils
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets

@RunWith(JUnit4::class)
class WeatherServiceTest {


    private lateinit var SUT: WeatherService

    private lateinit var mockWebServer: MockWebServer

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

//[UnitOfWork_StateUnderTest_ExpectedBehavior]
    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        SUT = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun httpRequest_weatherApiByCity_exactPath() {
        runBlocking {
            enqueueResponse("weather.json")
            val resultResponse = SUT.getWeatherByCity(CITY_NAME).body()
            val request = mockWebServer.takeRequest()
            assertNotNull(resultResponse)
            assertThat(request.path, `is`("/weather?units=metric&q=$CITY_NAME"))
        }
    }

    @Test
    fun httpRequest_weatherApiByCoord_exactPath() {
        runBlocking {
            enqueueResponse("weather.json")
            val resultResponse = SUT.getWeatherByCoord(LATITUDE, LONGITUDE).body()
            val request = mockWebServer.takeRequest()
            assertNotNull(resultResponse)
            assertThat(request.path, `is`("/weather?units=metric&lat=$LATITUDE&lon=$LONGITUDE"))
        }
    }

    @Test
    fun httpRequest_forecastApiByCoord_exactPath() {
        runBlocking {
            enqueueResponse("weather.json")
            val resultResponse = SUT.getWeatherForecast(LATITUDE, LONGITUDE).body()
            val request = mockWebServer.takeRequest()
            assertNotNull(resultResponse)
            assertThat(request.path, `is`("/forecast?units=metric&lat=$LATITUDE&lon=$LONGITUDE"))
        }
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader?.getResourceAsStream("api-response/$fileName")
        val source = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse.setBody(source)
        )
    }
}
