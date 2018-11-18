package com.sterlingng.views

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class TitleLabelIconView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private val mTitleTextView: TextView
    private val mLabelTextView: TextView
    private val mIconImageView: ImageView

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.TitleLabelIconView, 0, 0)
        val drawable = a.getDrawable(R.styleable.TitleLabelIconView_drawable)
        val title = a.getString(R.styleable.TitleLabelIconView_title)
        val label = a.getString(R.styleable.TitleLabelIconView_label)

        LayoutInflater.from(context).inflate(R.layout.title_label_icon_view, this, true)
        val root = this[0] as ConstraintLayout

        mTitleTextView = root[1] as TextView
        mTitleTextView.text = title

        mLabelTextView = root[2] as TextView
        mLabelTextView.text = label

        mIconImageView = root[0] as ImageView
        mIconImageView.setImageDrawable(drawable)

        a.recycle()
    }

    var title: String
        get() = mTitleTextView.text.toString()
        set(value) {
            mTitleTextView.text = value
        }

    var label
        get() = mLabelTextView.text.toString()
        set(value) {
            mLabelTextView.text = value
        }

    private operator fun ViewGroup.get(position: Int): View = getChildAt(position)
}