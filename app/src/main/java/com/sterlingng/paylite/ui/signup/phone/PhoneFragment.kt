package com.sterlingng.paylite.ui.signup.phone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.signup.SignUpActivity
import com.sterlingng.paylite.utils.AppConstants
import com.sterlingng.paylite.utils.AppConstants.EVENT_ONE
import com.sterlingng.paylite.utils.AppConstants.EVENT_SEVEN
import com.sterlingng.paylite.utils.AppUtils.createEvent
import com.sterlingng.paylite.utils.AppUtils.createId
import com.sterlingng.paylite.utils.OnChildDidClickNext
import com.sterlingng.views.LargeLabelEditText
import javax.inject.Inject

class PhoneFragment : BaseFragment(), PhoneMvpView {

    @Inject
    lateinit var mPresenter: PhoneMvpContract<PhoneMvpView>

    private lateinit var mPhoneEditText: LargeLabelEditText
    lateinit var mDidClickNext: OnChildDidClickNext
    lateinit var exit: ImageView
    lateinit var next: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_phone, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        next = view.findViewById(R.id.next_phone)
        mPhoneEditText = view.findViewById(R.id.phone)
    }

    override fun setUp(view: View) {
        next.setOnClickListener {
            if (mPhoneEditText.text.isEmpty() || mPhoneEditText.text.length != 10) {
                show("Phone number is required", true)
                return@setOnClickListener
            }

            val data = HashMap<String, Any>()
            data["mobile"] = "0${mPhoneEditText.text}"
            mPresenter.sendOtp(data)
        }

        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }
    }

    override fun onSendOTPFailed() {
        show("Error sending OTP. Please ensure you've entered a valid phone number", true)
    }

    override fun onSendOTPSuccessful() {
        createEvent(
                baseActivity,
                AppConstants.ON_BOARDING,
                EVENT_ONE,
                EVENT_SEVEN,
                createId(),
                (baseActivity as SignUpActivity).latitude,
                (baseActivity as SignUpActivity).longitude,
                javaClass.simpleName,
                ""
        )
        mDidClickNext.onNextClick(arguments?.getInt(INDEX)!!, mPhoneEditText.text)
    }


    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    companion object {

        private const val INDEX = "PhoneFragment.INDEX"

        fun newInstance(index: Int): PhoneFragment {
            val fragment = PhoneFragment()
            val args = Bundle()
            args.putInt(INDEX, index)
            fragment.arguments = args
            return fragment
        }
    }
}