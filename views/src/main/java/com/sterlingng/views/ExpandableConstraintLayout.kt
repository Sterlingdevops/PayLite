package com.sterlingng.views

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.util.Log
import android.view.animation.Animation
import android.view.animation.Transformation


class ExpandableConstraintLayout : ConstraintLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun expand() {
        val initialHeight = height

        measure(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        val targetHeight = measuredHeight

        val distanceToExpand = targetHeight - initialHeight

        val a = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                if (interpolatedTime == 1f) {
                    // Do this after expanded
                }
                layoutParams.height = (initialHeight + distanceToExpand * interpolatedTime).toInt()
                requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        a.duration = distanceToExpand.toLong()
        startAnimation(a)
    }

    fun collapse(collapsedHeight: Int) {
        val initialHeight = measuredHeight

        val distanceToCollapse = initialHeight - collapsedHeight

        val a = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                if (interpolatedTime == 1f) {
                    // Do this after collapsed
                }

                Log.i(TAG, "Collapse | InterpolatedTime = $interpolatedTime")

                layoutParams.height = (initialHeight - distanceToCollapse * interpolatedTime).toInt()
                requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        a.duration = distanceToCollapse.toLong()
        startAnimation(a)
    }

    companion object {
        const val TAG = "ExpandableCL"
    }
}