package com.sterlingng.paylite.ui.fund.amount

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
import com.blackcat.currencyedittext.CurrencyEditText
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseDialog
import javax.inject.Inject

class AmountBottomSheetFragment : BaseDialog(), View.OnClickListener, AmountMvpView {

    @Inject
    lateinit var mPresenter: AmountMvpContract<AmountMvpView>

    var mCardAmount: String = "0.00"
    private lateinit var mAddButton: Button
    lateinit var onAddAmountListener: OnAddAmount
    private lateinit var mCloseImageView: ImageView
    private lateinit var mCardAmountTextInputEditText: CurrencyEditText
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

        val view = LayoutInflater.from(context).inflate(R.layout.fragment_amount_bottom_sheet, null)

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
        mAddButton.setOnClickListener {
            onAddAmountListener.onAddAmount(dialog, mCardAmountTextInputEditText.text.toString())
        }

        mCloseImageView.setOnClickListener {
            dialog.dismiss()
        }

        mCardAmountTextInputEditText.setText(mCardAmount)
    }

    override fun show(message: String, useToast: Boolean) {
        baseActivity.show(message, useToast)
    }

    override fun bindViews(view: View) {
        mAddButton = view.findViewById(R.id.add)
        mCloseImageView = view.findViewById(R.id.close)
        mCardAmountTextInputEditText = view.findViewById(R.id.card_amount_edit_text)
    }

    interface OnAddAmount {
        fun onAddAmount(dialog: Dialog, amount: String)
    }

    companion object {

        fun newInstance(): AmountBottomSheetFragment {
            val dialog = AmountBottomSheetFragment()
            val args = Bundle()
            dialog.arguments = args
            return dialog
        }
    }
}
