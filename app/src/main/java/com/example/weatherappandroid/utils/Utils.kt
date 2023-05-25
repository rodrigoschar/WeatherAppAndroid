package com.example.weatherappandroid.utils

import android.text.Html
import android.text.Spanned

object Utils {
    const val appId = "439d4b804bc8187953eb36d2a8c26a02"

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

    fun setFirstTextBold(bold: String, normal: String): Spanned {
        val sourceString = "<b>$bold</b> $normal"
        return Html.fromHtml(sourceString, Html.FROM_HTML_MODE_LEGACY)
    }
}