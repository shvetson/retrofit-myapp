package ru.shvets.myappretrofit.util

import android.view.View
import android.widget.ProgressBar
import kotlin.math.roundToInt

/** @author Oleg Shvets shvetson@gmail.com on 2022-09-25 */

fun ProgressBar.hide() {
    this.visibility = View.GONE
}

fun ProgressBar.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun roundDouble(value: Double, count: Double): Double{
    // count - 10.0, 100.0, 1000.0 - количество цифр после запятой
    return (value * count).roundToInt() / count
}

fun String.concat(text: String): String {
    return this + text
}