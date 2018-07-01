package com.sterlingng.paylite.utils.widgets

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Rect
import android.support.design.widget.TextInputLayout
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.Interpolator
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.sterlingng.paylite.R
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method


/**
 * TextInputLayout temporary workaround for helper text showing
 */
class CustomTextInputLayout : TextInputLayout {

    private var bounds: Rect? = null
    private var mErrorEnabled = false
    private var mHelperTextEnabled = false
    private var mHelperView: TextView? = null
    private var recalculateMethod: Method? = null
    private var mHelperText: CharSequence? = null
    private var collapsingTextHelper: Any? = null
    private var mHelperTextColor: ColorStateList? = null
    private var mHelperTextAppearance = R.style.HelperTextAppearance

    constructor(_context: Context) : super(_context)

    constructor(_context: Context, _attrs: AttributeSet) : super(_context, _attrs) {
        val a = context.obtainStyledAttributes(_attrs, R.styleable.CustomTextInputLayout, 0, 0)
        try {
            mHelperTextColor = a.getColorStateList(R.styleable.CustomTextInputLayout_helperTextColor)
            mHelperText = a.getText(R.styleable.CustomTextInputLayout_helperText)
        } finally {
            a.recycle()
        }
        init()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        adjustBounds()
    }

    private fun init() {
        try {
            val cthField = TextInputLayout::class.java.getDeclaredField("mCollapsingTextHelper")
            cthField.isAccessible = true
            collapsingTextHelper = cthField.get(this)

            val boundsField = collapsingTextHelper?.javaClass?.getDeclaredField("mCollapsedBounds")
            boundsField?.isAccessible = true
            bounds = boundsField?.get(collapsingTextHelper) as Rect

            recalculateMethod = collapsingTextHelper?.javaClass?.getDeclaredMethod("recalculate")
        } catch (e: NoSuchFieldException) {
            collapsingTextHelper = null
            bounds = null
            recalculateMethod = null
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            collapsingTextHelper = null
            bounds = null
            recalculateMethod = null
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            collapsingTextHelper = null
            bounds = null
            recalculateMethod = null
            e.printStackTrace()
        }
    }

    private fun adjustBounds() {
        if (collapsingTextHelper == null) {
            return
        }

        try {
            bounds?.left = editText!!.left + editText!!.paddingLeft
            recalculateMethod?.invoke(collapsingTextHelper)
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        super.addView(child, index, params)
        if (child is EditText) {
            if (!TextUtils.isEmpty(mHelperText)) {
                setHelperText(mHelperText!!)
            }
        }
    }

    fun setHelperTextColor(_helperTextColor: ColorStateList) {
        mHelperTextColor = _helperTextColor
    }

    private fun setHelperText(_helperText: CharSequence) {
        mHelperText = _helperText
        if (!this.mHelperTextEnabled) {
            if (TextUtils.isEmpty(mHelperText)) {
                return
            }
            this.setHelperTextEnabled(true)
        }

        if (!TextUtils.isEmpty(mHelperText)) {
            this.mHelperView!!.text = mHelperText
            this.mHelperView!!.visibility = View.VISIBLE
            ViewCompat.setAlpha(this.mHelperView!!, 0.0f)
            ViewCompat.animate(this.mHelperView)
                    .alpha(1.0f).setDuration(200L)
                    .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
                    .setListener(null).start()
        } else if (this.mHelperView!!.visibility == View.VISIBLE) {
            ViewCompat.animate(this.mHelperView)
                    .alpha(0.0f).setDuration(200L)
                    .setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR)
                    .setListener(object : ViewPropertyAnimatorListenerAdapter() {
                        override fun onAnimationEnd(view: View?) {
                            mHelperView!!.text = null
                            mHelperView!!.visibility = View.INVISIBLE
                        }
                    }).start()
        }
        this.sendAccessibilityEvent(2048)
    }

    private fun setHelperTextEnabled(_enabled: Boolean) {
        if (mHelperTextEnabled == _enabled) return
        if (_enabled && mErrorEnabled) {
            isErrorEnabled = false
        }
        if (this.mHelperTextEnabled != _enabled) {
            if (_enabled) {
                this.mHelperView = TextView(this.context)
                this.mHelperView?.setTextAppearance(this.context, this.mHelperTextAppearance)
                if (mHelperTextColor != null) {
                    this.mHelperView?.setTextColor(mHelperTextColor)
                }
                this.mHelperView?.visibility = View.INVISIBLE
                val view = getChildAt(1)
                if (view != null && view is LinearLayout) {
                    (view as ViewGroup).addView(mHelperView, 0)
                } else {
                    this.addView(this.mHelperView)
                    if (this.mHelperView != null) {
                        ViewCompat.setPaddingRelative(
                                this.mHelperView,
                                ViewCompat.getPaddingStart(editText),
                                0, ViewCompat.getPaddingEnd(editText),
                                editText!!.paddingBottom)
                    }
                }
            } else {
                this.removeView(this.mHelperView)
                this.mHelperView = null
            }

            this.mHelperTextEnabled = _enabled
        }
    }

    override fun setErrorEnabled(_enabled: Boolean) {
        if (mErrorEnabled == _enabled) return
        mErrorEnabled = _enabled
        if (_enabled && mHelperTextEnabled) {
            setHelperTextEnabled(false)
        }

        super.setErrorEnabled(_enabled)

        if (!(_enabled || TextUtils.isEmpty(mHelperText))) {
            setHelperText(mHelperText!!)
        }
    }

    companion object {

        internal val FAST_OUT_SLOW_IN_INTERPOLATOR: Interpolator = FastOutSlowInInterpolator()
    }
}