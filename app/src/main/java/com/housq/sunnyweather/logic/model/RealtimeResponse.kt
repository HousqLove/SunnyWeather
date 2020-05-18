package com.housq.sunnyweather.logic.model

import com.google.gson.annotations.SerializedName

data class RealtimeResponse(val status: String, val result: Result) {

    data class Result(val realtime: Realtime)

    class Realtime(
        val skycon: String, val temperature: Float,
        @SerializedName("air_quality") val airQuality: AirQuelity
    )

    class AirQuelity(val aqi: AQI)

    class AQI(val chn: Float)
}
