package com.sterlingng.paylite.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.util.regex.Pattern

class CardExpiryTextWatcher(private val editText: EditText) : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
        val pattern = Pattern.compile("(?:[0-9]{1,4})")
        val matcher = pattern.matcher(s)
        if (editText.text.toString().length == 4 && matcher.matches()) {
            val stringBuilder = StringBuilder(editText.text)
            val anotherStringBuilder = StringBuilder()

            anotherStringBuilder.append(stringBuilder[0])
            anotherStringBuilder.append(stringBuilder[1])
            anotherStringBuilder.append('/')
            anotherStringBuilder.append(stringBuilder[2])
            anotherStringBuilder.append(stringBuilder[3])

            editText.setText(anotherStringBuilder.toString())
            editText.setSelection(editText.length())
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }
}