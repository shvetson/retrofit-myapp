package ru.shvets.myappretrofit.data.weather

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Data>,
    val wind: Wind
) {
    data class Clouds(
        val all: Int
    )

    data class Coord(
        val lat: Double,
        val lon: Double
    )

    data class Main(
        @SerializedName("feels_like")
        val feelsLike: Double,
        @SerializedName("grnd_level")
        val grnLevel: Int,
        val humidity: Int,
        val pressure: Int,
        @SerializedName("sea_level")
        val seaLevel: Int,
        val temp: Double,
        @SerializedName("temp_max")
        val tempMax: Double,
        @SerializedName("temp_min")
        val tempMin: Double
    )

    data class Sys(
        val country: String,
        val id: Int,
        val sunrise: Int,
        val sunset: Int,
        val type: Int
    )

    data class Data(
        val description: String,
        val icon: String,
        val id: Int,
        val main: String
    )

    data class Wind(
        val deg: Int,
        val gust: Double,
        val speed: Double
    )
}

