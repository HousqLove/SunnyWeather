package com.housq.sunnyweather.logic.dao

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.housq.sunnyweather.SunnyWeatherApplication
import com.housq.sunnyweather.logic.model.Place

object PlaceDao {

    fun savePlace(place: Place) {
        sharedPreferences().edit {
            putString("place", Gson().toJson(place))
        }
    }

    fun getSavedPlace(): Place {
        val string = sharedPreferences().getString("place", "")
        return Gson().fromJson(string, Place::class.java)
    }

    fun isSaved() = sharedPreferences().contains("place")

    private fun sharedPreferences() =
        SunnyWeatherApplication.context.getSharedPreferences("sunnt_weather", Context.MODE_PRIVATE)
}