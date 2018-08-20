package com.sterlingng.paylite.utils

import android.text.TextUtils
import android.util.Patterns

fun String.toSentenceCase(): String {
    val stringBuilder = StringBuilder()
    for (str in split(" ")) {
        stringBuilder.append(str.substring(0, 1).toUpperCase())
        stringBuilder.append(str.substring(1).toLowerCase())
        stringBuilder.append(" ")
    }
    return stringBuilder.toString()
}

fun String.isValidEmail(): Boolean {
    return !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}