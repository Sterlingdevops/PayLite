package com.sterlingng.views

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class TitleLabelTextView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private val mTitleTextView: TextView
    private val mLabelTextView: TextView

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.TitleLabelTextView, 0, 0)
        val title = a.getString(R.styleable.TitleLabelTextView_title)
        val label = a.getString(R.styleable.TitleLabelTextView_label)

        LayoutInflater.from(context).inflate(R.layout.title_label_text_view, this, true)
        val root = this[0] as ConstraintLayout

        mLabelTextView = root[1] as TextView
        mLabelTextView.text = label

        mTitleTextView = root[0] as TextView
        mTitleTextView.text = title

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