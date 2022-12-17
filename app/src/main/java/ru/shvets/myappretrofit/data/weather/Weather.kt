package ru.shvets.myappretrofit.data.weather

data class Weather(
    val city: String,
    val temp: Double,
    val desc: String,
    val icon: String
)
