package com.housq.sunnyweather.logic

import androidx.lifecycle.liveData
import com.housq.sunnyweather.logic.dao.PlaceDao
import com.housq.sunnyweather.logic.model.Place
import com.housq.sunnyweather.logic.model.Weather
import com.housq.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import retrofit2.await
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

object Repository {

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }

    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        val placeResponse = SunnyWeatherNetwork.placeService.searchPlaces(query).await()
        if (placeResponse.status == "ok") {
            Result.success(placeResponse.places)
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtime = async {
                SunnyWeatherNetwork.weatherService.getRealtimeWeather(lng, lat).await()
            }

            val deferredDaily = async {
                SunnyWeatherNetwork.weatherService.getDailyWeather(lng, lat).await()
            }

            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather =
                    Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            } else {
                Result.failure(
                    RuntimeException(
                        "realtime response status is ${realtimeResponse.status}" +
                                "daily response status is ${dailyResponse.status}"
                    )
                )
            }

        }
    }

    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getSavedPlace() = PlaceDao.getSavedPlace()

    fun isPlaceSaved() = PlaceDao.isSaved()


}