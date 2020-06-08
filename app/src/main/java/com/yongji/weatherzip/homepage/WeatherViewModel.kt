package com.yongji.weatherzip.homepage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yongji.weatherzip.network.WeatherApi
import com.yongji.weatherzip.network.WeatherResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, ERROR, DONE }

class WeatherViewModel : ViewModel() {

    private val _status = MutableLiveData<ApiStatus>()

    val status: LiveData<ApiStatus>
        get() = _status

    private val _response = MutableLiveData<WeatherResponse>()

    val weatherResponse: LiveData<WeatherResponse>
        get() = _response

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    /**
     * Call getWeather() on init so we can display result immediately.
     */
    init {
        getWeather()
    }

    private fun getWeather() {
        coroutineScope.launch {
            var getWeatherDeferred2 = WeatherApi.retrofitService.getWeatherResponse("observation", "94538", "true", "ZFErCZF5Gk7zCvnL6CsLGxSjD1oIHYdg-FFTAa9apsM")
            try {
                _status.value = ApiStatus.LOADING
                val listResult = getWeatherDeferred2.await()

                _status.value = ApiStatus.DONE
                _response.value = listResult

            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _response.value = null
            }
        }
    }

    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}