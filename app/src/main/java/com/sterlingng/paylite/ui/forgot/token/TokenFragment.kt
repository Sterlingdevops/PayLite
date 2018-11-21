package com.sterlingng.paylite.ui.forgot.token


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.davidmiguel.numberkeyboard.NumberKeyboard
import com.davidmiguel.numberkeyboard.NumberKeyboardListener
import com.goodiebag.pinview.PinView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.forgot.ForgotActivity
import com.sterlingng.paylite.utils.OnChildDidClickNext
import javax.inject.Inject

class TokenFragment : BaseFragment(), TokenMvpView, NumberKeyboardListener {

    @Inject
    lateinit var mPresenter: TokenMvpContract<TokenMvpView>

    private lateinit var exit: ImageView
    private lateinit var mPinView: PinView
    private lateinit var mNumberKeyboard: NumberKeyboard

    lateinit var mDidClickNext: OnChildDidClickNext

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_token_forgot, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        mPinView = view.findViewById(R.id.pin_view)
        mNumberKeyboard = view.findViewById(R.id.numberKeyboard)
    }

    override fun setUp(view: View) {
        mNumberKeyboard.setListener(this)

        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        mPinView.setPinViewEventListener { _, _ ->
            val data = HashMap<String, Any>()
            with((baseActivity as ForgotActivity).forgotPasswordRequest) {
                data["mobile"] = "0$mobile"
                data["Otp"] = mPinView.value
            }
            mPresenter.validateOtp(data)
            hideKeyboard()
        }
    }

    override fun onValidateOtpFailed() {
        show("An error occurred validating the OTP, please try again", true)
        mPinView.clearValue()
    }

    override fun onValidateOtpSuccessful() {
        mDidClickNext.onNextClick(arguments?.getInt(INDEX)!!, mPinView.value)
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    override fun onNumberClicked(number: Int) {
        mPinView.append(number.toString())
    }

    override fun onLeftAuxButtonClicked() {

    }

    override fun onRightAuxButtonClicked() {
        mPinView.backSpaceClicked()
    }

    override fun logout() {
        baseActivity.logout()
    }

    companion object {

        private const val INDEX = "TokenFragment.INDEX"

        fun newInstance(index: Int): TokenFragment {
            val fragment = TokenFragment()
            val args = Bundle()
            args.putInt(INDEX, index)
            fragment.arguments = args
            return fragment
        }
    }
}
