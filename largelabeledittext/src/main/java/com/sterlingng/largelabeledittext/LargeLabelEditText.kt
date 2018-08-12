package com.sterlingng.largelabeledittext

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.text.InputFilter
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView

class LargeLabelEditText(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var mHintTextView: TextView
    private var mTextEditText: EditText

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.LargeLabelEditText, 0, 0)
        val color = a.getColor(R.styleable.LargeLabelEditText_textColor, ContextCompat.getColor(context, R.color.dark_sky_blue))
        val type = a.getInt(R.styleable.LargeLabelEditText_android_inputType, EditorInfo.TYPE_TEXT_VARIATION_NORMAL)
        val textLength = a.getInt(R.styleable.LargeLabelEditText_maxLength, Int.MAX_VALUE)
        val drawable = a.getDrawable(R.styleable.LargeLabelEditText_drawable)
        val hint = a.getString(R.styleable.LargeLabelEditText_hint)
        val text = a.getString(R.styleable.LargeLabelEditText_text)

        LayoutInflater.from(context).inflate(R.layout.largelabeledittext, this, true)
        val root = this[0] as ConstraintLayout

        mHintTextView = root[1] as TextView
        mHintTextView.setTextColor(color)
        mHintTextView.text = hint

        mTextEditText = root[0] as EditText
        mTextEditText.hint = text
        mTextEditText.inputType = type
        mTextEditText.setTextColor(color)
        mTextEditText.filters = listOf<InputFilter>(InputFilter.LengthFilter(textLength)).toTypedArray()
        mTextEditText.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)

        a.recycle()
    }

    operator fun ViewGroup.get(position: Int): View = getChildAt(position)
}