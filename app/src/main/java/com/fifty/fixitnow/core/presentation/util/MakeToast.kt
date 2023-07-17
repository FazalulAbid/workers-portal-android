package com.fifty.fixitnow.core.presentation.util

import android.content.Context
import android.widget.Toast

fun makeToast(message: String, context: Context, isLong: Boolean = false) {
    Toast.makeText(
        context,
        message,
        if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    ).show()
}

fun makeToast(messageRes: Int, context: Context, isLong: Boolean = false) {
    Toast.makeText(
        context,
        context.getString(messageRes),
        if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    ).show()
}

fun makeToast(messageRes: Int, formatArg: Int, context: Context, isLong: Boolean = false) {
    Toast.makeText(
        context,
        context.getString(messageRes, formatArg),
        if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    ).show()
}