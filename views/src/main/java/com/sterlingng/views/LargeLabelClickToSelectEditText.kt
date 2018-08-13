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

    var mLabelTextView: TextView
    var mTextEditText: ClickToSelectEditText<T>

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.LargeLabelClickToSelectEditText, 0, 0)
        val tint = a.getColor(R.styleable.LargeLabelClickToSelectEditText_android_drawableTint, ContextCompat.getColor(context, R.color.black))
        val color = a.getColor(R.styleable.LargeLabelClickToSelectEditText_textColor, ContextCompat.getColor(context, R.color.dark_sky_blue))
        var drawable = a.getDrawable(R.styleable.LargeLabelClickToSelectEditText_drawable)
        val hint = a.getString(R.styleable.LargeLabelClickToSelectEditText_hint)
        val text = a.getString(R.styleable.LargeLabelClickToSelectEditText_label)

        LayoutInflater.from(context).inflate(R.layout.large_label_click_to_select_edit_text, this, true)
        val root = this[0] as ConstraintLayout

        mLabelTextView = root[1] as TextView
        mLabelTextView.setTextColor(color)
        mLabelTextView.text = text

        mTextEditText = root[0] as ClickToSelectEditText<T>
        mTextEditText.hint = hint
        mTextEditText.setTextColor(color)
        drawable = DrawableCompat.wrap(drawable)
        DrawableCompat.setTint(drawable, tint)
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN)
        mTextEditText.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)

        a.recycle()
    }

    fun label(): String = mLabelTextView.text.toString()

    operator fun ViewGroup.get(position: Int): View = getChildAt(position)
}