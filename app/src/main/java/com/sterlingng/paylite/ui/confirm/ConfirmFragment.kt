package com.sterlingng.paylite.ui.confirm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.davidmiguel.numberkeyboard.NumberKeyboard
import com.davidmiguel.numberkeyboard.NumberKeyboardListener
import com.goodiebag.pinview.PinView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseDialog
import javax.inject.Inject

class ConfirmFragment : BaseDialog(), ConfirmMvpView, NumberKeyboardListener {

    @Inject
    lateinit var mPresenter: ConfirmMvpContract<ConfirmMvpView>

    private lateinit var mPinView: PinView
    var onPinValidatedListener: OnPinValidated? = null
    private lateinit var mNumberKeyboard: NumberKeyboard

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_confirm, container, false)

        val component = activityComponent
        dialog.setContentView(view)
        component.inject(this)
        mPresenter.onAttach(this)

        if (onPinValidatedListener == null) throw IllegalStateException("onPinValidatedListener not implemented")

        showsDialog = false
        bindViews(view)
        setUp(view)

        return view
    }

    override fun getPeekHeight(): Int {
        val displayMetrics = baseActivity.resources.displayMetrics
        val height = displayMetrics.heightPixels
        return (height * 0.8).toInt()
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
        onPinValidatedListener?.onPinCorrect()
    }

    override fun onPinEnteredIncorrect() {
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
