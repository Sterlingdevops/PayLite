package com.sterlingng.paylite.utils

import android.content.Context
import android.graphics.Canvas
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AppCompatEditText
import android.util.AttributeSet
import android.widget.ListAdapter

class ClickToSelectEditText<T> : AppCompatEditText {

    private var mHint: CharSequence

    var sender = -1
    lateinit var onItemSelectedListener: OnItemSelectedListener<T>
    var mSpinnerAdapter: ListAdapter? = null
        set(value) {
            field = value
            configureOnClickListener(sender)
        }

    constructor(context: Context) : super(context) {
        mHint = hint
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mHint = hint
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mHint = hint
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        isClickable = true
        isFocusable = false
        isLongClickable = false
    }

    private fun configureOnClickListener(sender: Int) {
        setOnClickListener { view ->
            val builder = AlertDialog.Builder(view.context)
            builder.setTitle(mHint)
            builder.setAdapter(mSpinnerAdapter) { _, selectedIndex ->
                onItemSelectedListener.onItemSelectedListener(sender, mSpinnerAdapter?.getItem(selectedIndex) as T, selectedIndex)
            }
            builder.setPositiveButton(android.R.string.cancel, null)
            builder.create().show()
        }
    }

    interface OnItemSelectedListener<T> {
        fun onItemSelectedListener(sender: Int, item: T, selectedIndex: Int)
    }
}