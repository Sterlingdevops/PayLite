package com.sterlingng.views

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class TitleIconView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private val mTitleTextView: TextView
    private val mIconImageView: ImageView

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.TitleIconView, 0, 0)
        val drawable = a.getDrawable(R.styleable.TitleIconView_drawable)
        val title = a.getString(R.styleable.TitleIconView_title)

        LayoutInflater.from(context).inflate(R.layout.title_icon_view, this, true)
        val root = this[0] as ConstraintLayout

        mTitleTextView = root[1] as TextView
        mTitleTextView.text = title

        mIconImageView = root[0] as ImageView
        mIconImageView.setImageDrawable(drawable)

        a.recycle()
    }

    var title: String
        get() = mTitleTextView.text.toString()
        set(value) {
            mTitleTextView.text = value
        }

    private operator fun ViewGroup.get(position: Int): View = getChildAt(position)
}