package ru.shvets.myweather.util

/** @author Oleg Shvets shvetson@gmail.com on 2022-09-25 */

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}