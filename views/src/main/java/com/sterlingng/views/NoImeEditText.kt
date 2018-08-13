package com.sterlingng.views

import android.content.Context
import android.support.design.widget.TextInputEditText
import android.util.AttributeSet


class NoImeEditText(context: Context, attrs: AttributeSet) : TextInputEditText(context, attrs) {
    override fun onCheckIsTextEditor(): Boolean {
        return false
    }
}