package com.sterlingng.paylite.ui.signup.otp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.goodiebag.pinview.PinView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.signup.SignUpActivity
import com.sterlingng.paylite.utils.OnChildDidClickNext
import javax.inject.Inject

class OtpFragment : BaseFragment(), OtpMvpView {

    @Inject
    lateinit var mPresenter: OtpMvpContract<OtpMvpView>

    lateinit var mDidClickNext: OnChildDidClickNext
    private lateinit var mPinView: PinView
    private lateinit var exit: ImageView
    private lateinit var next: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_otp, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        next = view.findViewById(R.id.next_otp)
        mPinView = view.findViewById(R.id.pin_view)
    }

    override fun setUp(view: View) {
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

        next.setOnClickListener {
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
        mDidClickNext.onNextClick(arguments?.getInt(INDEX)!!, mPinView.value)
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

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
