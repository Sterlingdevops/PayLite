package com.sterlingng.paylite.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.NumberFormat
import java.util.*

class CurrencyTextWatcher(private val editText: EditText) : TextWatcher {
    private fun formatDecimal(number: Float): String {
        val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US)
        return currencyFormatter.format(number.toDouble()).substring(1)
    }

    override fun afterTextChanged(s: Editable) {
        if (s.isNotEmpty()) {
            val value = formatDecimal(editText.toString().toFloat())
            s.replace(0, value.length - 1, value)
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }
}