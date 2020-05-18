package com.housq.sunnyweather.logic.network

import retrofit2.await

object SunnyWeatherNetwork {

    val placeService = ServiceCreator.create<PlaceService>()

    val weatherService = ServiceCreator.create<WeatherService>()

//    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()
}