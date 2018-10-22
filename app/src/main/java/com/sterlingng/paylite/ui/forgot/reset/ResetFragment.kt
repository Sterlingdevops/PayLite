package com.sterlingng.paylite.ui.forgot.reset


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.ui.forgot.ForgotActivity
import com.sterlingng.paylite.ui.signup.complete.CompleteFragment
import com.sterlingng.paylite.utils.OnChildDidClickNext
import com.sterlingng.views.LargeLabelEditText
import javax.inject.Inject

class ResetFragment : BaseFragment(), ResetMvpView {

    @Inject
    lateinit var mPresenter: ResetMvpContract<ResetMvpView>

    lateinit var mDidClickNext: OnChildDidClickNext

    private lateinit var next: Button
    private lateinit var exit: ImageView
    private lateinit var mPasswordTextInputEditText: LargeLabelEditText
    private lateinit var mConfirmPasswordTextInputEditText: LargeLabelEditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_reset, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        next = view.findViewById(R.id.next_password)
        mPasswordTextInputEditText = view.findViewById(R.id.password)
        mConfirmPasswordTextInputEditText = view.findViewById(R.id.confirm_password)
    }

    override fun setUp(view: View) {
        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        next.setOnClickListener {
            if (mPasswordTextInputEditText.text.isEmpty() || mConfirmPasswordTextInputEditText.text.isEmpty()) {
                show("Password fields cannot be empty", false)
                return@setOnClickListener
            }

            if (mPasswordTextInputEditText.text.length != 4 || mConfirmPasswordTextInputEditText.text.length != 4) {
                show("The passwords should be 4 characters long", false)
                return@setOnClickListener
            }

            if (mPasswordTextInputEditText.text != mConfirmPasswordTextInputEditText.text) {
                show("Passwords do not match", false)
                return@setOnClickListener
            }

            val data = HashMap<String, Any>()
            data["Password"] = mPasswordTextInputEditText.text
            if (arguments?.getInt(INDEX)!! != -1)
                data["PhoneNumber"] = "0${(baseActivity as ForgotActivity)
                        .forgotPasswordRequest.mobile}"
            mPresenter.resetPassword(data, arguments?.getInt(INDEX)!!)
        }
    }

    override fun onUpdatePasswordFailed() {
        show("An error occurred, please try again", false)
    }

    override fun onUpdatePasswordSuccessful() {
        if (arguments?.getInt(INDEX)!! == -1) {
            (baseActivity as DashboardActivity)
                    .mNavController
                    .pushFragment(CompleteFragment.newInstance("Your login PIN has been changed"))
        } else {
            mDidClickNext.onNextClick(arguments?.getInt(INDEX)!!, "")
            hideKeyboard()
        }
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    companion object {

        private const val INDEX = "ResetFragment.INDEX"

        fun newInstance(index: Int): ResetFragment {
            val fragment = ResetFragment()
            val args = Bundle()
            args.putInt(INDEX, index)
            fragment.arguments = args
            return fragment
        }
    }
}
