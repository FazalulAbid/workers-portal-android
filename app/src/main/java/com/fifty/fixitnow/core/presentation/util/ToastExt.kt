package com.fifty.fixitnow.core.presentation.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

object ToastExt {
    private var toast: Toast? = null
    fun makeText(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
        toast?.cancel()
        toast = Toast.makeText(context, message, duration)
        toast?.show()
    }

    fun makeText(context: Context, message: Int, duration: Int = Toast.LENGTH_SHORT) {
        toast?.cancel()
        toast = Toast.makeText(context, context.getString(message), duration)
        toast?.show()
    }
}
//
//fun makeToast(message: String, context: Context, isLong: Boolean = false) {
//    Toast.makeText(
//        context,
//        message,
//        if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
//    ).show()
//}
//
//fun makeToast(messageRes: Int, context: Context, isLong: Boolean = false) {
//    Toast.makeText(
//        context,
//        context.getString(messageRes),
//        if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
//    ).show()
//}
//
//fun makeToast(messageRes: Int, formatArg: Int, context: Context, isLong: Boolean = false) {
//    Toast.makeText(
//        context,
//        context.getString(messageRes, formatArg),
//        if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
//    ).show()
//}