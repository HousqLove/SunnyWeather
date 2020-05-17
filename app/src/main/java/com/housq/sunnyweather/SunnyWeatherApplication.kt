package com.housq.sunnyweather

import android.app.Application
import android.content.Context

class SunnyWeatherApplication : Application() {

    companion object {
        lateinit var context: Context
        const val CAIYUN_TOKEN = "7rOEOVWT8uOWoj3G"
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}