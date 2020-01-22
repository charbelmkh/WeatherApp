package com.domain.weatherapp.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.domain.weatherapp.core.data.remote.Result
import com.domain.weatherapp.core.di.connection.ConnectionManager
import com.domain.weatherapp.data.Contants.Companion.CITY_NAME
import com.domain.weatherapp.data.Contants.Companion.SEVEN_CITIES_NAME
import com.domain.weatherapp.weather.data.repo.impl.WeatherRepositoryImpl
import com.domain.weatherapp.weather.domain.impl.WeatherUseCaseImpl
import org.junit.Assert;
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class WeatherUseCasesTest {


    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()


    lateinit var useCase: WeatherUseCaseImpl
    private val repo = mock(WeatherRepositoryImpl::class.java)
    private val connectionManager = spy(ConnectionManager::class.java)

    val scope= CoroutineScope(IO)
    //[UnitOfWork_StateUnderTest_ExpectedBehavior]

    @Before
    fun setup(){
        useCase= WeatherUseCaseImpl(
            repo,
            connectionManager
        )
    }

    @Test
    fun getWeater_noInternet_returnErrorResult() {
        `when`(connectionManager.isNetworkAvailable()).thenReturn(false)
         useCase.subscribeToWeather(CITY_NAME,scope).observeForever({
             Assert.assertTrue(it.status==Result.Status.ERROR)
        })


    }
    @Test
    fun getWeater_minRequiredCities_returnErrorResultBecauseRequiredThreeMinCities() {
        `when`(connectionManager.isNetworkAvailable()).thenReturn(true)
        useCase.subscribeToWeather(CITY_NAME,scope).observeForever({
            Assert.assertTrue(it.status==Result.Status.ERROR)
        })


    }
    @Test
    fun getWeater_maxRequiredCities_returnErrorResultBecauseRequiredSEVENMaxCities() {
        `when`(connectionManager.isNetworkAvailable()).thenReturn(true)
        useCase.subscribeToWeather(CITY_NAME,scope).observeForever({
            Assert. assertTrue(it.status==Result.Status.ERROR)
        })


    }
    @Test
    fun getWeater_sevenCities_returnErrorResultBecauseRequiredSEVENMaxCities() {
        `when`(connectionManager.isNetworkAvailable()).thenReturn(true)
        useCase.subscribeToWeather(SEVEN_CITIES_NAME,scope).observeForever({
            Assert. assertTrue(it.status==Result.Status.LOADING)
        })


    }


}