package com.sterlingng.views

import android.content.Context
import android.graphics.PorterDuff
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class LargeLabelClickToSelectEditText<T>(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var mLabelTextView: TextView
    var mTextEditText: ClickToSelectEditText<T>

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.LargeLabelClickToSelectEditText, 0, 0)
        val tint = a.getColor(R.styleable.LargeLabelClickToSelectEditText_android_drawableTint, ContextCompat.getColor(context, R.color.black))
        val color = a.getColor(R.styleable.LargeLabelClickToSelectEditText_textColor, ContextCompat.getColor(context, R.color.dark_sky_blue))
        val hintColor = a.getColor(R.styleable.LargeLabelClickToSelectEditText_hintColor, ContextCompat.getColor(context, R.color.gray))
        val plainText: String? = a.getString(R.styleable.LargeLabelClickToSelectEditText_android_text)
        var drawable = a.getDrawable(R.styleable.LargeLabelClickToSelectEditText_drawable)
        val hint = a.getString(R.styleable.LargeLabelClickToSelectEditText_hint)
        val text = a.getString(R.styleable.LargeLabelClickToSelectEditText_label)

        LayoutInflater.from(context).inflate(R.layout.large_label_click_to_select_edit_text, this, true)
        val root = this[0] as ConstraintLayout

        mLabelTextView = root[1] as TextView
        mLabelTextView.setTextColor(hintColor)
        mLabelTextView.text = text

        mTextEditText = root[0] as ClickToSelectEditText<T>
        mTextEditText.hint = hint
        plainText?.let { mTextEditText.setText(it) }
        mTextEditText.setTextColor(color)
        drawable = DrawableCompat.wrap(drawable!!)
        DrawableCompat.setTint(drawable!!, tint)
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN)
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