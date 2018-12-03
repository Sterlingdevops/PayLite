package com.sterlingng.paylite.ui.signup.otp

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
import com.sterlingng.paylite.ui.signup.SignUpActivity
import com.sterlingng.paylite.utils.AppConstants
import com.sterlingng.paylite.utils.AppConstants.EVENT_SEVEN
import com.sterlingng.paylite.utils.AppConstants.EVENT_TWO
import com.sterlingng.paylite.utils.AppUtils
import com.sterlingng.paylite.utils.OnChildDidClickNext
import javax.inject.Inject

class OtpFragment : BaseFragment(), OtpMvpView, NumberKeyboardListener {

    @Inject
    lateinit var mPresenter: OtpMvpContract<OtpMvpView>

    private lateinit var mNumberKeyboard: NumberKeyboard
    lateinit var mDidClickNext: OnChildDidClickNext
    private lateinit var mPinView: PinView
    private lateinit var exit: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_otp, container, false)
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
            with((baseActivity as SignUpActivity).signUpRequest) {
                data["mobile"] = phoneNumber
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
        AppUtils.createEvent(
                baseActivity,
                AppConstants.ON_BOARDING,
                EVENT_TWO,
                EVENT_SEVEN,
                AppUtils.createId(),
                (baseActivity as SignUpActivity).latitude,
                (baseActivity as SignUpActivity).longitude,
                javaClass.simpleName,
                ""
        )
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

    companion object {
        private const val INDEX = "OtpFragment.INDEX"
        fun newInstance(index: Int): OtpFragment {
            val fragment = OtpFragment()
            val args = Bundle()
            args.putInt(INDEX, index)
            fragment.arguments = args
            return fragment
        }
    }
}
