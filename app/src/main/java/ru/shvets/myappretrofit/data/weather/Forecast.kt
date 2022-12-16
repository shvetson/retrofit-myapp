package ru.shvets.myappretrofit.data.weather

import com.google.gson.annotations.SerializedName

data class Forecast(
    val city: City,
    val cnt: Int,
    val cod: String,
    @SerializedName("list")
    val hourly: ArrayList<HourlyWeather>,
    val message: Int
) {

    data class City(
        val id: Int,
        val coord: Coord,
        val country: String,
        val name: String,
        val population: Int,
        val sunrise: Int,
        val sunset: Int,
        val timezone: Int
    )

    data class Coord(
        val lat: Double,
        val lon: Double
    )

    data class HourlyWeather(
        val dt: Long,
        val main: Main,
        val weather: ArrayList<Weather>,
        val clouds: Clouds,
        val wind: Wind,
        val visibility: Int,
        val pop: Double,
        val sys: Sys,
        @SerializedName("dt_txt")
        val date: String
    )

    data class Clouds(
        val all: Int
    )

    data class Main(
        @SerializedName("feels_like")
        val feelsLike: Double,
        @SerializedName("grnd_level")
        val grdLevel: Int,
        val humidity: Int,
        val pressure: Int,
        @SerializedName("sea_level")
        val seaLevel: Int,
        val temp: Double,
        @SerializedName("temp_kf")
        val tempKf: Double,
        @SerializedName("temp_max")
        val tempMax: Double,
        @SerializedName("temp_min")
        val tempMin: Double
    )

    data class Sys(
        val pod: String
    )

    data class Weather(
        val id: Int,
        val main: String,
        val description: String,
        val icon: String
    )

    data class Wind(
        val deg: Int,
        val gust: Double,
        val speed: Double
    )
}