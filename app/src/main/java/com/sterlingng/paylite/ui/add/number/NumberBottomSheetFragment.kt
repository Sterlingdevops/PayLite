package com.sterlingng.paylite.ui.add.number


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
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseDialog
import mostafa.ma.saleh.gmail.com.editcredit.EditCredit
import javax.inject.Inject

class NumberBottomSheetFragment : BaseDialog(), View.OnClickListener, NumberMvpView {

    @Inject
    lateinit var mPresenter: NumberMvpContract<NumberMvpView>

    var mCardNumber: String = ""
    lateinit var onAddListener: OnAdd
    private lateinit var mAddButton: Button
    private lateinit var mCloseImageView: ImageView
    private lateinit var mCardNumberEditCredit: EditCredit
    private val mBottomSheetBehaviorCallback = object : BottomSheetBehavior.BottomSheetCallback() {

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

        val view = LayoutInflater.from(context).inflate(R.layout.fragment_card_number_bottom_sheet, null)

        val component = activityComponent
        dialog.setContentView(view)
        component.inject(this)
        mPresenter.onAttach(this)

        val params = (view.parent as View).layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior

        if (behavior != null && behavior is BottomSheetBehavior<*>) {
            behavior.setBottomSheetCallback(mBottomSheetBehaviorCallback)
        }

        bindViews(view)
        setUp(view)
    }

    override fun onClick(view: View) {

    }

    override fun setUp(view: View) {
        mCardNumberEditCredit.setText(mCardNumber)
        mAddButton.setOnClickListener {

            onAddListener.onAddNumber(dialog,
                    mCardNumberEditCredit.textWithoutSeparator,
                    mCardNumberEditCredit.mCurrentDrawableResId,
                    mCardNumberEditCredit.isCardValid)
        }
        mCloseImageView.setOnClickListener {
            dialog.dismiss()
        }
    }

    override fun show(message: String, useToast: Boolean) {
        baseActivity.show(message, useToast)
    }

    override fun bindViews(view: View) {
        mAddButton = view.findViewById(R.id.add)
        mCloseImageView = view.findViewById(R.id.close)
        mCardNumberEditCredit = view.findViewById(R.id.card_number)
    }

    interface OnAdd {
        fun onAddNumber(dialog: Dialog, cardNumber: String, logo: Int, valid: Boolean)
    }

    companion object {

        fun newInstance(): NumberBottomSheetFragment {
            val dialog = NumberBottomSheetFragment()
            val args = Bundle()
            dialog.arguments = args
            return dialog
        }
    }
}
