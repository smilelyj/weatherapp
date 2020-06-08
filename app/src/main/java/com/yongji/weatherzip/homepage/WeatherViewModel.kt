package com.yongji.weatherzip.homepage

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yongji.weatherzip.R
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
     * Call getTWeather() on init so we can display result immediately.
     */
    init {
        print("city")
        getTWeather()
    }

    private fun getTWeather() {
        coroutineScope.launch {
            var getWeatherDeferred2 = WeatherApi.retrofitService.getWeatherResponse()

            Log.d("Tag",getWeatherDeferred2.await().toString() )

            try {
                _status.value = ApiStatus.LOADING
                val listResult = getWeatherDeferred2.await()
                Log.d("Tag",getWeatherDeferred2.await().toString() )

                _status.value = ApiStatus.DONE
                _response.value = listResult

            } catch (e: Exception) {
                Log.d("TAG", "error city")

                Log.d("Tag",e.toString() )
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