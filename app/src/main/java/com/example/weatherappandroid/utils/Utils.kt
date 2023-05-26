package com.example.weatherappandroid.utils

import android.text.Html
import android.text.Spanned

object Utils {
    const val appId = ""
    const val appIdA = ""

    fun convertToCelsius(temp: Double): String {
        val celsius = temp - 273.15
        val result : Double = String.format("%.2f", celsius).toDouble()
        return "$result Cº"
    }

    fun getWeatherImage(icon: String): String {
        return "https://openweathermap.org/img/wn/${icon}@2x.png"
    }

    fun getFlagImage(icon: String): String {
        return "https://flagsapi.com/${icon}/flat/64.png"
    }

    fun getSearchWeatherByCityUrl(cityName: String) : String {
        return "find?q=${cityName}&appid=${appId}&units=metric"
    }

    fun getCurrentWeatherUrl(latitude: Double, longitude: Double): String {
        return "https://api.openweathermap.org/data/2.5/weather?lat=${latitude}&lon=${longitude}&appid=${appIdA}"
    }

    fun setFirstTextBold(bold: String, normal: String): Spanned {
        val sourceString = "<b>$bold</b> $normal"
        return Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY)
    }
}