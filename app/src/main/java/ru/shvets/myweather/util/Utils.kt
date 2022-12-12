package ru.shvets.myweather.util

import android.view.View
import android.widget.ProgressBar

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