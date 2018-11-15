package com.sterlingng.views

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

    var mLabelTextView: TextView
    var mTextEditText: EditText

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.LargeLabelEditText, 0, 0)
        val color = a.getColor(R.styleable.LargeLabelEditText_textColor, ContextCompat.getColor(context, R.color.black))
        val type = a.getInt(R.styleable.LargeLabelEditText_android_inputType, EditorInfo.TYPE_TEXT_VARIATION_NORMAL)
        val enabled = a.getBoolean(R.styleable.LargeLabelEditText_android_enabled, true)
        val plainText: String? = a.getString(R.styleable.LargeLabelClickToSelectEditText_android_text)
        val textLength = a.getInt(R.styleable.LargeLabelEditText_maxLength, Int.MAX_VALUE)
        val drawable = a.getDrawable(R.styleable.LargeLabelEditText_drawable)
        val text = a.getString(R.styleable.LargeLabelEditText_label)
        val hint = a.getString(R.styleable.LargeLabelEditText_hint)

        LayoutInflater.from(context).inflate(R.layout.large_label_edit_text, this, true)
        val root = this[0] as ConstraintLayout

        mLabelTextView = root[1] as TextView
        mLabelTextView.setTextColor(color)
        mLabelTextView.text = text

        mTextEditText = root[0] as EditText
        mTextEditText.hint = hint
        mTextEditText.inputType = type
        mTextEditText.isEnabled = enabled
        mTextEditText.setTextColor(color)
        plainText?.let { mTextEditText.setText(it) }
        mTextEditText.filters = listOf<InputFilter>(InputFilter.LengthFilter(textLength)).toTypedArray()
        mTextEditText.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)

        a.recycle()
    }

    var text: String
        get() = mTextEditText.text.toString()
        set(value) {
            mTextEditText.setText(value)
        }

    var label: String
        get() = mLabelTextView.text.toString()
        set(value) {
            mLabelTextView.text = value
        }

    operator fun ViewGroup.get(position: Int): View = getChildAt(position)
}