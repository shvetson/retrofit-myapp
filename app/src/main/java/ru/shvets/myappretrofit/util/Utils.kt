package ru.shvets.myappretrofit.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

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

private fun hasAccessURL(url: String): Boolean {
    return try {
        val command = "ping -c 1 $url"
        Runtime.getRuntime().exec(command).waitFor() == 0
    } catch (e: Exception) {
        false
    }
}

fun String.upper(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.ROOT
        ) else it.toString()
    }
}

fun fromDate(date: Date?): Long? {
    return date?.time
}

fun toDate(date: Long): Date {
    return Date(date * 1000)
}

fun formatDate(date: Date): String {
    val formatter = SimpleDateFormat("EEEE, d MMMM", Locale.getDefault())
//    val formatter = SimpleDateFormat("EEEE, d MMMM yyyy - HH:mm", Locale.getDefault())
    return formatter.format(date)
}

fun formatDateShort(date: Date): String {
    val formatter = SimpleDateFormat("dd.MM HH:mm", Locale.getDefault())
    return formatter.format(date)
}

internal fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, /*flags*/ 0)
}

internal fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, /*flags*/ 0)
}