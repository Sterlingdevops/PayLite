package com.sterlingng.paylite.ui.forgot.reset


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
import com.sterlingng.paylite.ui.forgot.ForgotActivity
import com.sterlingng.paylite.utils.OnChildDidClickNext
import javax.inject.Inject

class ResetPasswordFragment : BaseFragment(), ResetPasswordMvpView, NumberKeyboardListener {

    @Inject
    lateinit var mPresenter: ResetPasswordMvpContract<ResetPasswordMvpView>

    lateinit var mDidClickNext: OnChildDidClickNext

    private lateinit var mNumberKeyboard: NumberKeyboard
    private lateinit var mSubtitleTextView: TextView
    private lateinit var mTitleTextView: TextView
    private lateinit var mPinView: PinView
    private lateinit var exit: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_reset, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        mPinView = view.findViewById(R.id.pin_view)
        mTitleTextView = view.findViewById(R.id.title)
        mSubtitleTextView = view.findViewById(R.id.subtitle)
        mNumberKeyboard = view.findViewById(R.id.numberKeyboard)
    }

    @SuppressLint("SetTextI18n")
    override fun setUp(view: View) {
        mNumberKeyboard.setListener(this)
        val index = arguments?.getInt(INDEX)!!

        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        when (index) {
            3 -> {
                mTitleTextView.text = "Create a PIN"
                mSubtitleTextView.text = "To protect your account"
            }
            4 -> {
                mTitleTextView.text = "Confirm a PIN"
                mSubtitleTextView.text = "To protect your account"
            }
        }

        mPinView.setPinViewEventListener { _, _ ->
            val password = mPinView.value

            when (index) {
                3 -> {
                    mDidClickNext.onNextClick(arguments?.getInt(INDEX)!!, mPinView.value)
                }
                4 -> {
                    with((baseActivity as ForgotActivity).forgotPasswordRequest) {
                        if (password == newPassword) {
                            val data = HashMap<String, Any>()
                            data["Password"] = newPassword
                            data["PhoneNumber"] = "0$mobile"
                            mPresenter.resetPassword(data)
                        } else
                            show("Passwords do no match", true)
                    }
                }
            }
        }
    }

    override fun onUpdatePasswordFailed() {
        show("An error occurred, please try again", false)
    }

    override fun onUpdatePasswordSuccessful() {
        mDidClickNext.onNextClick(arguments?.getInt(INDEX)!!, "")
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

        private const val INDEX = "ResetPasswordFragment.INDEX"

        fun newInstance(index: Int): ResetPasswordFragment {
            val fragment = ResetPasswordFragment()
            val args = Bundle()
            args.putInt(INDEX, index)
            fragment.arguments = args
            return fragment
        }
    }
}
