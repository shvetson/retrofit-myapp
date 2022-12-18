package ru.shvets.myappretrofit.data.weather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Weather(
    val id: String = UUID.randomUUID().toString(),
    val city: String,
    val lon: Float,
    val lat: Float,
    val temp: Double,
    val feelsLike: Double,
    val desc: String,
    val icon: String
): Parcelable