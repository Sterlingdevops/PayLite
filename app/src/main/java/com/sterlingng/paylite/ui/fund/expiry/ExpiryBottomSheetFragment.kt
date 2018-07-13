package com.sterlingng.paylite.ui.fund.expiry


import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.TextInputEditText
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseDialog
import com.sterlingng.paylite.utils.CardExpiryTextWatcher
import javax.inject.Inject

class ExpiryBottomSheetFragment : BaseDialog(), View.OnClickListener, ExpiryMvpView {

    @Inject
    lateinit var mPresenter: ExpiryMvpContract<ExpiryMvpView>

    var mCardExpiry: String = ""
    private lateinit var mAddButton: Button
    lateinit var onAddExpiryListener: OnAddExpiry
    private lateinit var mCloseImageView: ImageView
    private lateinit var mCardExpiryTextInputEditText: TextInputEditText
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

        val view = LayoutInflater.from(context).inflate(R.layout.fragment_expiry_bottom_sheet, null)

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
            onAddExpiryListener.onAddExpiry(dialog, mCardExpiryTextInputEditText.text.toString())
        }
        mCloseImageView.setOnClickListener {
            dialog.dismiss()
        }
        mCardExpiryTextInputEditText.setText(mCardExpiry)
    }

    override fun show(message: String, useToast: Boolean) {
        baseActivity.show(message, useToast)
    }

    override fun bindViews(view: View) {
        mAddButton = view.findViewById(R.id.add)
        mCloseImageView = view.findViewById(R.id.close)
        mCardExpiryTextInputEditText = view.findViewById(R.id.card_expiry_edit_text)
        mCardExpiryTextInputEditText.addTextChangedListener(CardExpiryTextWatcher(mCardExpiryTextInputEditText))
    }

    interface OnAddExpiry {
        fun onAddExpiry(dialog: Dialog, expiry: String)
    }

    companion object {

        fun newInstance(): ExpiryBottomSheetFragment {
            val dialog = ExpiryBottomSheetFragment()
            val args = Bundle()
            dialog.arguments = args
            return dialog
        }
    }
}
