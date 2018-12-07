package com.sterlingng.paylite.ui.confirm

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.CoordinatorLayout
import android.view.LayoutInflater
import android.view.View
import com.davidmiguel.numberkeyboard.NumberKeyboard
import com.davidmiguel.numberkeyboard.NumberKeyboardListener
import com.goodiebag.pinview.PinView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseDialog
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import javax.inject.Inject

class ConfirmFragment : BaseDialog(), ConfirmMvpView, NumberKeyboardListener {
    @Inject
    lateinit var mPresenter: ConfirmMvpContract<ConfirmMvpView>

    private lateinit var mPinView: PinView
    var onPinValidatedListener: OnPinValidated? = null
    private lateinit var mNumberKeyboard: NumberKeyboard

    private val mBottomSheetBehaviorCallback =
            object : BottomSheetBehavior.BottomSheetCallback() {

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        dismiss()
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {

                }
            }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(baseActivity, theme)
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)

        val view = LayoutInflater.from(context).inflate(R.layout.fragment_confirm, null)

        val component = activityComponent
        dialog.setContentView(view)
        component.inject(this)
        mPresenter.onAttach(this)

        val params = (view.parent as View).layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior

        if (behavior != null && behavior is BottomSheetBehavior<*>) {
            behavior.setBottomSheetCallback(mBottomSheetBehaviorCallback)

            val displayMetrics = baseActivity.resources.displayMetrics
            val height = displayMetrics.heightPixels

            val maxHeight = (height * 0.88).toInt()
            val mBehavior = BottomSheetBehavior.from(view.parent as View)
            mBehavior.peekHeight = maxHeight
        }

        if (onPinValidatedListener == null) throw IllegalStateException("onPinValidatedListener not implemented")

        bindViews(view)
        setUp(view)
    }

    override fun bindViews(view: View) {
        mPinView = view.findViewById(R.id.pin_view)
        mNumberKeyboard = view.findViewById(R.id.numberKeyboard)
    }

    override fun setUp(view: View) {
        mNumberKeyboard.setListener(this)
        mPinView.setPinViewEventListener { _, _ ->
            mPresenter.validatePin(mPinView.value)
        }
    }

    override fun onPinEnteredCorrect() {
        hideKeyboard()
        onPinValidatedListener?.onPinCorrect()
        (baseActivity as DashboardActivity).mNavController.clearDialogFragment()
    }

    override fun dismiss() {
        super.dismiss()
        hideKeyboard()
    }

    override fun onPinEnteredIncorrect() {
        hideKeyboard()
        onPinValidatedListener?.onPinIncorrect()
    }

    override fun show(message: String, useToast: Boolean) {
        baseActivity.show(message, useToast)
    }

    override fun onNumberClicked(number: Int) {
        mPinView.append(number.toString())
    }

    override fun onLeftAuxButtonClicked() {

    }

    override fun onRightAuxButtonClicked() {
        mPinView.backSpaceClicked()
    }

    companion object {

        fun newInstance(): ConfirmFragment {
            val fragment = ConfirmFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    interface OnPinValidated {
        fun onPinCorrect()
        fun onPinIncorrect()
    }
}