package com.sterlingng.paylite.ui.confirm

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.CoordinatorLayout
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.goodiebag.pinview.PinView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseDialog
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import javax.inject.Inject

class ConfirmFragment : BaseDialog(), ConfirmMvpView {
    @Inject
    lateinit var mPresenter: ConfirmMvpContract<ConfirmMvpView>

    private lateinit var next: Button
    private lateinit var exit: ImageView
    private lateinit var mPinView: PinView
    var onPinValidatedListener: OnPinValidated? = null

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
        }

        if (onPinValidatedListener == null) throw IllegalStateException("onPinValidatedListener not implemented")

        bindViews(view)
        setUp(view)
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        next = view.findViewById(R.id.next)
        mPinView = view.findViewById(R.id.pin_view)
    }

    override fun setUp(view: View) {
        exit.setOnClickListener {
            (baseActivity as DashboardActivity).mNavController.clearDialogFragment()
        }

        mPinView.setPinViewEventListener { _, _ ->
            mPresenter.validatePin(mPinView.value)
        }

        next.setOnClickListener {
            mPresenter.validatePin(mPinView.value)
        }
    }

    override fun onPinEnteredCorrect() {
        onPinValidatedListener?.onPinCorrect()
        (baseActivity as DashboardActivity).mNavController.clearDialogFragment()
    }

    override fun dismiss() {
        super.dismiss()
        hideKeyboard()
    }

    override fun onPinEnteredIncorrect() {
        onPinValidatedListener?.onPinIncorrect()
    }

    override fun show(message: String, useToast: Boolean) {
        baseActivity.show(message, useToast)
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
