package com.sterlingng.paylite.ui.signup.pin

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.davidmiguel.numberkeyboard.NumberKeyboard
import com.davidmiguel.numberkeyboard.NumberKeyboardListener
import com.goodiebag.pinview.PinView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.signup.SignUpActivity
import com.sterlingng.paylite.utils.AppConstants
import com.sterlingng.paylite.utils.AppConstants.EVENT_FIVE
import com.sterlingng.paylite.utils.AppConstants.EVENT_SEVEN
import com.sterlingng.paylite.utils.AppConstants.EVENT_SIX
import com.sterlingng.paylite.utils.AppUtils
import com.sterlingng.paylite.utils.OnChildDidClickNext
import javax.inject.Inject

class PinFragment : BaseFragment(), PinMvpView, NumberKeyboardListener {

    @Inject
    lateinit var mPresenter: PinMvpContract<PinMvpView>

    lateinit var mDidClickNext: OnChildDidClickNext

    private lateinit var mNumberKeyboard: NumberKeyboard
    private lateinit var mTitleTextView: TextView
    private lateinit var mPinView: PinView
    private lateinit var exit: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up_pin, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun setUp(view: View) {
        mNumberKeyboard.setListener(this)

        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        val index = arguments?.getInt(INDEX)!!

        when (index) {
            5 -> mTitleTextView.text = "Create a PIN"
            6 -> mTitleTextView.text = "Confirm the PIN"
        }

        mPinView.setPinViewEventListener { _, _ ->
            val password = mPinView.value

            when (index) {
                5 -> {
                    AppUtils.createEvent(
                            baseActivity,
                            AppConstants.ON_BOARDING,
                            EVENT_FIVE,
                            EVENT_SEVEN,
                            AppUtils.createId(),
                            (baseActivity as SignUpActivity).latitude,
                            (baseActivity as SignUpActivity).longitude,
                            javaClass.simpleName,
                            ""
                    )
                    mDidClickNext.onNextClick(arguments?.getInt(INDEX)!!, mPinView.value)
                }
                6 -> {
                    if (password == (baseActivity as SignUpActivity).signUpRequest.password)
                        mPresenter.doSignUp((baseActivity as SignUpActivity).signUpRequest.toHashMap())
                    else
                        show("Passwords do no match", true)
                }
            }
        }
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        mPinView = view.findViewById(R.id.pin_view)
        mTitleTextView = view.findViewById(R.id.title)
        mNumberKeyboard = view.findViewById(R.id.numberKeyboard)
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    override fun onDoSignUpFailed(failureReason: String) {
        show(failureReason.replace("(Duplicate Records)", ""), true)
    }

    override fun onDoSignUpSuccessful() {
        AppUtils.createEvent(
                baseActivity,
                AppConstants.ON_BOARDING,
                EVENT_SIX,
                EVENT_SEVEN,
                AppUtils.createId(),
                (baseActivity as SignUpActivity).latitude,
                (baseActivity as SignUpActivity).longitude,
                javaClass.simpleName,
                ""
        )
        mDidClickNext.onNextClick(arguments?.getInt(INDEX)!!, mPinView.value)
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

        private const val INDEX = "PinFragment.INDEX"

        fun newInstance(index: Int): PinFragment {
            val fragment = PinFragment()
            val args = Bundle()
            args.putInt(INDEX, index)
            fragment.arguments = args
            return fragment
        }
    }
}
