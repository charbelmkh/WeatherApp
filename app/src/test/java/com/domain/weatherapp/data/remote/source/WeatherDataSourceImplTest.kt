package com.domain.weatherapp.data.remote.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.domain.weatherapp.data.Contants.Companion.CITY_NAME
import com.domain.weatherapp.weather.data.remote.services.WeatherService
import com.domain.weatherapp.weather.data.remote.source.impl.WeatherDataSourceImpl
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class WeatherDataSourceImplTest {


    private lateinit var service:WeatherService

    private lateinit var SUT: WeatherDataSourceImpl

    private lateinit var mockWebServer: MockWebServer


    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()


    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherService::class.java)
        SUT =
            WeatherDataSourceImpl(
                service
            )
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun dataSource_weatherByCity_makeSureMessageNotNull() {
        runBlocking {
            enqueueResponse()
            val resultResponse = SUT.getWeatherByCity(CITY_NAME)
            assertNull(resultResponse.data)
            assertNotNull(resultResponse.message)
            assertFalse(resultResponse.message!!.isEmpty());

        }
    }


    private fun enqueueResponse() {
        val mockResponse = MockResponse()
        mockResponse.status="404"

        mockWebServer.enqueue(
            mockResponse
        )
    }
}
